# Subscription Management App

A beautiful, modern Android application built with Jetpack Compose for managing and tracking your subscriptions. Features a glassmorphic design system with smooth animations and a comprehensive UI following Material Design 3 principles.

## 🎨 Features Implemented

### Design System
- **Color Palette**: Custom color scheme with Primary (#696FC7), Secondary (#A7AAE1), Accent Peach (#F5D3C4), and Accent Pink (#F2AEBB)
- **Typography**: Complete type scale with system default font (ready for Poppins font integration)
- **Glassmorphism**: Translucent cards with subtle borders and backdrop effects
- **Gradient Backgrounds**: Beautiful vertical gradients throughout the app

### Screens
1. **Onboarding** - 3-page introduction with horizontal pager
2. **Dashboard** - Hero card showing monthly total, subscription list with renewal indicators
3. **Add Subscription** - Form to add new subscriptions with validation
4. **Subscription Details** - Detailed view with large header, price history, and action buttons
5. **Analytics** - Spending trends, category breakdown, and upcoming renewals
6. **Settings** - Profile management, notifications, data export options

### Components
- **HeroCard**: Gradient card displaying total monthly spend (150dp height)
- **SubscriptionCard**: Glassmorphic card with avatar, details, and renewal pill (92dp height)
- **GradientFAB**: Circular 64dp FAB with gradient fill and breathing animation
- **PriceBadge**: Pill-shaped badge with pulse animation for price alerts
- **AppTopBar**: Floating transparent top bar with avatar and search
- **AppBottomNavigation**: Floating pill navigation with 4 icons + center FAB

### Animations
- **Entrance Animations**: 240ms fade-in and slide-up with ease-out-cubic
- **Press Animations**: Scale to 0.985 over 120ms on interactive elements
- **Breathing Animation**: Gentle FAB scale animation (1.00 → 1.03 over 3000ms)
- **Pulse Animation**: Price badge subtle scale pulse (1.02 → 0.98, 800ms loop)
- **Stagger Animations**: List items animate with 80ms delay per item

### Data Layer
- **Room Database**: Local SQLite database for subscription storage
- **Repository Pattern**: Clean architecture with SubscriptionRepository
- **Mock Data**: Pre-seeded with 6 sample subscriptions (Netflix, Spotify, Adobe, etc.)
- **ViewModels**: MVVM architecture with StateFlow for reactive UI updates

### Architecture
- **Jetpack Compose**: 100% declarative UI
- **Navigation Compose**: Type-safe navigation between screens
- **Material 3**: Latest Material Design components and theming
- **MVVM**: ViewModels with reactive state management
- **Room**: Local database with Flow-based queries

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- Kotlin 2.0.21
- Min SDK 28 (Android 9.0)
- Target SDK 36

### Building the Project

1. **Clone the repository**
2. **Open in Android Studio**
3. **Sync Gradle** - Let Android Studio download all dependencies
4. **Build & Run** - Click the Run button or use `./gradlew installDebug`

### First Launch
The app will automatically seed the database with 6 sample subscriptions on first launch.

## 📁 Project Structure

```
app/src/main/java/com/rahulxiao/subscriptionmanagementapp/
├── data/
│   ├── local/
│   │   ├── AppDatabase.kt          # Room database
│   │   ├── SubscriptionDao.kt      # Data access object
│   │   └── Converters.kt           # Type converters
│   ├── model/
│   │   └── Subscription.kt         # Subscription entity
│   └── repository/
│       └── SubscriptionRepository.kt
├── ui/
│   ├── animations/
│   │   └── Animations.kt           # Animation utilities
│   ├── components/
│   │   ├── HeroCard.kt
│   │   ├── SubscriptionCard.kt
│   │   ├── GradientFAB.kt
│   │   ├── PriceBadge.kt
│   │   ├── TopBar.kt
│   │   └── BottomNavigation.kt
│   ├── screens/
│   │   ├── OnboardingScreen.kt
│   │   ├── DashboardScreen.kt
│   │   ├── AddSubscriptionScreen.kt
│   │   ├── SubscriptionDetailsScreen.kt
│   │   ├── AnalyticsScreen.kt
│   │   └── SettingsScreen.kt
│   └── theme/
│       ├── Color.kt                # Color palette
│       ├── Type.kt                 # Typography scale
│       ├── Shape.kt                # Corner radii
│       └── Theme.kt                # App theme
├── viewmodel/
│   ├── DashboardViewModel.kt
│   ├── AddSubscriptionViewModel.kt
│   ├── DetailsViewModel.kt
│   ├── AnalyticsViewModel.kt
│   └── ViewModelFactory.kt
├── navigation/
│   └── NavGraph.kt                 # Navigation setup
└── MainActivity.kt                 # Entry point
```

## 🎯 Design Specifications

### Measurements (from UI mockup)
- **Screen Padding**: 16dp
- **Card Spacing**: 12-16dp
- **Corner Radii**: Small 12dp, Medium 18dp, Large 24dp
- **Hero Card Height**: 140-160dp
- **Subscription Card Height**: 92dp
- **Top Bar Height**: 56dp
- **Bottom Nav Height**: 86dp
- **FAB Size**: 64dp
- **Avatar Sizes**: 40dp (top bar), 56dp (cards), 80dp (profile)
- **Icon Size**: 24dp

### Colors
- Primary: `#696FC7` (Indigo Blue)
- Secondary: `#A7AAE1` (Soft Lavender)
- Accent Peach: `#F5D3C4`
- Accent Pink: `#F2AEBB`
- Text Primary: `#F5F7FA`
- Text Secondary: `#EDEFF8`
- Text Tertiary: `#CFCFE8`
- Glass Background: `rgba(255,255,255,0.10)`
- Glass Border: `rgba(255,255,255,0.12)`

## 🔧 Customization

### Adding Poppins Font
1. Download Poppins font files (Regular, Medium, SemiBold, Bold) from Google Fonts
2. Place `.ttf` files in `app/src/main/res/font/` directory
3. Update `ui/theme/Type.kt` to use the actual font files:
```kotlin
val PoppinsFontFamily = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold)
)
```

### Changing Colors
Edit `ui/theme/Color.kt` to customize the color palette.

### Modifying Animations
Edit `ui/animations/Animations.kt` to adjust animation durations and easing.

## 📱 Features to Add (Future)

- [ ] Backend integration for cloud sync
- [ ] Notification scheduling for renewal reminders
- [ ] Price change tracking and alerts
- [ ] Category management
- [ ] Data export (CSV)
- [ ] Multi-currency support
- [ ] Subscription templates for popular services
- [ ] Widgets for home screen
- [ ] Dark/Light theme toggle
- [ ] Biometric authentication

## 🧪 Testing

The project includes test directories:
- `src/test/` - Unit tests
- `src/androidTest/` - Instrumentation tests

Run tests with:
```bash
./gradlew test              # Unit tests
./gradlew connectedAndroidTest  # Instrumentation tests
```

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- UI Design inspired by modern subscription management apps
- Glassmorphism design trend
- Material Design 3 guidelines
- Jetpack Compose best practices

## 📞 Support

For issues, questions, or contributions, please open an issue on GitHub.

---

Built with ❤️ using Jetpack Compose

