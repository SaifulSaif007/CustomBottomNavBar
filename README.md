#  CustomBottomNavBar

An animated and customizable bottom navigation bar built in Jetpack Compose. Effortlessly style your app’s navigation with smooth transitions and flexibility.

##  Features

- Built entirely with **Jetpack Compose**
- Smooth animations for tab selection
- Easily customize icons, colors, shapes, and animations
- Simple configuration and integration

##  Setup

### 1. Add JitPack to your repositories

In your root `settings.gradle`:

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = 'https://jitpack.io' }
    }
}
```
Or in `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```
### 2. Add the dependency

![Latest Release](https://img.shields.io/github/v/release/SaifulSaif007/CustomBottomNavBar?style=flat-square)


In your module-level build.gradle.kts:
```kotlin
dependencies {
     implementation("com.github.SaifulSaif007:CustomBottomNavBar:<latest version>")
}
```

## Quick Usage
Below is a simple usage example:
```kotlin
@Composable
fun MyApp() {
    val navItems = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Search", Icons.Default.Search),
        NavItem("Profile", Icons.Default.Person)
    )
    var selected by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            CustomBottomNavBar(
                items = navItems,
                onSelectItem = { item, index ->
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                activeColor = Color.Cyan,
                inactiveColor = Color.Gray,
                // Other custom styling as needed
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Selected tab: ${navItems[selected].label}")
        }
    }
}

```
Simply adjust activeColor, inactiveColor, or other props to match your theme.

<br><br>

## MIT License

Copyright (c) 2025 Saiful Islam

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
