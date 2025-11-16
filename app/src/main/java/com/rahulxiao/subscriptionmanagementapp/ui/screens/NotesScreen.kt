package com.rahulxiao.subscriptionmanagementapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rahulxiao.subscriptionmanagementapp.ui.components.AppBottomNavigation
import com.rahulxiao.subscriptionmanagementapp.ui.components.AppTopBar
import com.rahulxiao.subscriptionmanagementapp.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Composable
fun NotesScreen(
    onNavigateToDashboard: () -> Unit,
    onNavigateToAdd: () -> Unit,
    onNavigateToAnalytics: () -> Unit,
    onNavigateToSettings: () -> Unit,
    currentRoute: String,
    modifier: Modifier = Modifier
) {
    var notes by remember { mutableStateOf(listOf<Note>()) }
    var showAddDialog by remember { mutableStateOf(false) }
    var editingNote by remember { mutableStateOf<Note?>(null) }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GradientStart, GradientEnd)
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Bar
            Spacer(modifier = Modifier.height(32.dp))
            AppTopBar(
                title = "Notes",
                avatarInitial = "U",
                onAvatarClick = onNavigateToSettings,
                modifier = Modifier.padding(horizontal = 0.dp)
            )
            
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                
                // Header with Add Button
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "My Notes",
                            style = MaterialTheme.typography.displayMedium
                        )
                        
                        IconButton(
                            onClick = { 
                                editingNote = null
                                showAddDialog = true 
                            },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(CornerRadiusSmall))
                                .background(Primary)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Note",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
                
                // Notes List
                if (notes.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "No notes yet",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = TextTertiary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Tap + to add your first note",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextTertiary
                                )
                            }
                        }
                    }
                } else {
                    items(notes.size) { index ->
                        NoteCard(
                            note = notes[index],
                            onEdit = { 
                                editingNote = notes[index]
                                showAddDialog = true
                            },
                            onDelete = { notes = notes.filter { it.id != notes[index].id } }
                        )
                    }
                }
                
                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
        
        // Bottom Navigation
        AppBottomNavigation(
            currentRoute = currentRoute,
            onNavigate = { route ->
                when (route) {
                    "dashboard" -> onNavigateToDashboard()
                    "analytics" -> onNavigateToAnalytics()
                    "settings" -> onNavigateToSettings()
                }
            },
            onAddClick = onNavigateToAdd,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
    
    // Add/Edit Note Dialog
    if (showAddDialog) {
        AddEditNoteDialog(
            note = editingNote,
            onDismiss = { 
                showAddDialog = false
                editingNote = null
            },
            onSave = { noteText ->
                if (editingNote != null) {
                    // Edit existing note
                    notes = notes.map { 
                        if (it.id == editingNote!!.id) 
                            it.copy(text = noteText, timestamp = System.currentTimeMillis())
                        else it 
                    }
                } else {
                    // Add new note
                    notes = notes + Note(text = noteText)
                }
                showAddDialog = false
                editingNote = null
            }
        )
    }
}

@Composable
private fun NoteCard(
    note: Note,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormat = remember { SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault()) }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(CornerRadiusMedium))
            .background(GlassBackground)
            .border(
                width = 1.dp,
                color = GlassBorder,
                shape = RoundedCornerShape(CornerRadiusMedium)
            )
            .clickable(onClick = onEdit)
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = note.text,
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextPrimary
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = dateFormat.format(Date(note.timestamp)),
                        style = MaterialTheme.typography.bodySmall,
                        color = TextTertiary
                    )
                }
                
                Row {
                    IconButton(
                        onClick = onEdit,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = AccentPink,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddEditNoteDialog(
    note: Note?,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var noteText by remember(note) { mutableStateOf(note?.text ?: "") }
    val isEditMode = note != null
    
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF2A2A2A),
        title = {
            Text(
                text = if (isEditMode) "Edit Note" else "Add Note",
                style = MaterialTheme.typography.displayMedium,
                color = TextPrimary
            )
        },
        text = {
            OutlinedTextField(
                value = noteText,
                onValueChange = { noteText = it },
                placeholder = { Text("Write your note here...", color = TextTertiary) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                maxLines = 8,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = GlassBorder,
                    cursorColor = Primary
                )
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    if (noteText.isNotBlank()) {
                        onSave(noteText)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary
                )
            ) {
                Text(if (isEditMode) "Save" else "Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextTertiary)
            }
        }
    )
}

