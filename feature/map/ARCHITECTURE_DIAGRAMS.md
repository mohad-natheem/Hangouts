# Feature.Map Architecture Diagrams

## Layer Dependency Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                     APPLICATION LAYER                            │
│  (Activity, Compose, Navigation, Application)                   │
└────────────────────────────────┬────────────────────────────────┘
                                 │ includes
                                 ▼
┌─────────────────────────────────────────────────────────────────┐
│                   PRESENTATION LAYER (feature.map.presentation)  │
│                                                                   │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │ DiscoverScreen (Jetpack Compose)                         │   │
│  │  - Renders map with markers                              │   │
│  │  - Shows location dialogs                                │   │
│  │  - Listens to state changes                              │   │
│  └────────────────┬─────────────────────────────────────────┘   │
│                   │ injects
│  ┌────────────────▼─────────────────────────────────────────┐   │
│  │ DiscoverViewModel                                         │   │
│  │  - Manages UI state (DiscoverUiState)                    │   │
│  │  - Emits events (DiscoverUiEvent)                        │   │
│  │  - Handles actions (DiscoverUiAction)                    │   │
│  │  - Orchestrates use cases                                │   │
│  │  ✓ NO Android Framework (except ViewModel)              │   │
│  │  ✓ NO Context parameter                                  │   │
│  │  ✓ NO direct API calls                                   │   │
│  └────────────────┬─────────────────────────────────────────┘   │
│                   │ depends on (uses)
│  └────────────────┼─────────────────────────────────────────┘   │
└────────────────────┼────────────────────────────────────────────┘
                     │ uses only
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│                   DOMAIN LAYER (feature.map.domain)              │
│  Business Logic - Framework Independent                         │
│                                                                   │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │ Use Cases:                                                │   │
│  │  ┌──────────────────────────────────────────────────┐    │   │
│  │  │ GetUserLocationUseCase                           │    │   │
│  │  │  - Check permission via repository              │    │   │
│  │  │  - Return NoPermission error if denied           │    │   │
│  │  │  - Fetch location from repository if granted     │    │   │
│  │  └──────────────────────────────────────────────────┘    │   │
│  │  ┌──────────────────────────────────────────────────┐    │   │
│  │  │ CheckLocationPermissionUseCase                   │    │   │
│  │  │  - Ask repository if permission granted          │    │   │
│  │  └──────────────────────────────────────────────────┘    │   │
│  │  ┌──────────────────────────────────────────────────┐    │   │
│  │  │ ShouldShowLocationPermissionRationaleUseCase    │    │   │
│  │  │  - Ask repository if rationale should be shown   │    │   │
│  │  └──────────────────────────────────────────────────┘    │   │
│  │  ┌──────────────────────────────────────────────────┐    │   │
│  │  │ GetPredefinedLocationsUseCase                    │    │   │
│  │  │  - Get list of searchable locations              │    │   │
│  │  └──────────────────────────────────────────────────┘    │   │
│  └────────────┬──────────────────────────────────────────────┘   │
│               │ uses (interface)
│  ┌────────────▼──────────────────────────────────────────────┐   │
│  │ LocationRepository (Interface)                            │   │
│  │  - hasLocationPermission(): Result<Boolean, LocationError>│   │
│  │  - shouldShowLocationPermissionRationale(): Result<...>  │   │
│  │  - getCurrentUserLocation(): Flow<Result<UserLocation>>  │   │
│  │  - getPredefinedLocations(): Flow<Result<List<...>>>     │   │
│  └────────────┬──────────────────────────────────────────────┘   │
│               │                                                   │
│  ┌────────────▼──────────────────────────────────────────────┐   │
│  │ Models:                                                    │   │
│  │  - UserLocation(latitude, longitude)                      │   │
│  │  - LocationModel(name, latitude, longitude, zoom)        │   │
│  │  - LocationError (sealed: NoPermission, Unknown, ...)    │   │
│  └──────────────────────────────────────────────────────────┘   │
│                                                                   │
│  ✓ ZERO Android Framework imports                              │
│  ✓ ZERO Android context                                        │
│  ✓ Pure business logic, testable on JVM                        │
└────────────────────┬─────────────────────────────────────────────┘
                     │ implemented by
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│                   DATA LAYER (feature.map.data)                  │
│  Implementation Details - Android Framework Access              │
│                                                                   │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │ LocationRepositoryImpl (implements LocationRepository)   │   │
│  │  - Delegates to data sources and managers              │   │
│  │  - Coordinates between multiple sources                │   │
│  └────────────┬───────────────┬───────────────┬────────────┘   │
│               │               │               │                  │
│  ┌────────────▼──┐ ┌──────────▼─────────┐  ┌─▼──────────────┐   │
│  │RemoteLocation │ │LocationPermission  │  │LocalLocationData│  │
│  │DataSource     │ │Manager             │  │Source           │  │
│  │               │ │                    │  │                 │  │
│  │Uses:          │ │Uses:               │  │Returns:         │  │
│  │LocationFetcher│ │ContextCompat      │  │Hardcoded        │  │
│  │               │ │ActivityCompat      │  │locations        │  │
│  └───────────────┘ └────────────────────┘  └─────────────────┘  │
│       │                    │                                      │
│  ┌────▼──────────────────────▼──────────────────────────────┐   │
│  │ Managers (Android API wrappers):                         │   │
│  │  ┌─────────────────────────────────────────────────┐    │   │
│  │  │ LocationFetcher                                 │    │   │
│  │  │  - FusedLocationProviderClient.lastLocation    │    │   │
│  │  │  - Wraps device location access               │    │   │
│  │  └─────────────────────────────────────────────────┘    │   │
│  │  ┌─────────────────────────────────────────────────┐    │   │
│  │  │ LocationPermissionManager                       │    │   │
│  │  │  - ContextCompat.checkSelfPermission()        │    │   │
│  │  │  - ActivityCompat.shouldShowRequestRationale()│    │   │
│  │  │  - Returns Result<Boolean, LocationError>     │    │   │
│  │  └─────────────────────────────────────────────────┘    │   │
│  └──────────────────────────────────────────────────────────┘   │
│                                                                   │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │ Dependency Injection (featureMapDataModule):            │   │
│  │  - Provides LocationFetcher (singleton)                 │   │
│  │  - Provides LocationPermissionManager (singleton)       │   │
│  │  - Provides RemoteLocationDataSource (factory)          │   │
│  │  - Provides LocalLocationDataSource (singleton)         │   │
│  │  - Provides LocationRepositoryImpl (singleton)           │   │
│  └──────────────────────────────────────────────────────────┘   │
│                                                                   │
│  ✓ ALL Android Framework access HERE                            │
│  ✓ Implementation details isolated                              │
│  ✓ Can be mocked or swapped easily                              │
└─────────────────────────────────────────────────────────────────┘
```

---

## Data Flow: Fetch User Location

```
┌─────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                        │
│                                                              │
│  User taps "Get My Location" button                        │
│         ↓                                                    │
│  DiscoverScreen                                             │
│  { viewModel.fetchUserLocation() }                          │
└─────────────────┬───────────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                        │
│                                                              │
│  DiscoverViewModel.fetchUserLocation()                      │
│  {                                                           │
│    _uiState.update { it.copy(isLoading = true) }           │
│                                                              │
│    getUserLocationUseCase().collect { result ->            │
│      when (result) {                                        │
│        Success → handleSuccess()                            │
│        Error   → handleError()                              │
│      }                                                       │
│    }                                                         │
│  }                                                           │
└─────────────────┬───────────────────────────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────────────────────────┐
│                    DOMAIN LAYER                              │
│                                                              │
│  GetUserLocationUseCase.invoke()                            │
│  {                                                           │
│    STEP 1: Check if permission granted                      │
│    val hasPermission =                                      │
│      locationRepository.hasLocationPermission()             │
│                                                              │
│    if (!hasPermission.data) {                               │
│      emit(Result.Error(LocationError.NoPermission))         │
│      return@flow                                            │
│    }                                                         │
│                                                              │
│    STEP 2: Permission granted, fetch location               │
│    locationRepository.getCurrentUserLocation()              │
│      .collect { emit(it) }                                  │
│  }                                                           │
└──────────────┬──────────────────────────────────────────────┘
               │
        ┌──────┴──────┐
        ▼             ▼
┌─────────────┐  ┌────────────────────────────────────────────┐
│   DATA      │  │         DATA LAYER                         │
│   LAYER     │  │                                            │
│             │  │ LocationRepository.hasLocationPermission() │
│             │  │                                            │
│             │  │ ← LocationPermissionManager.hasLocation()  │
│             │  │   ├→ ContextCompat.checkSelfPermission()   │
│             │  │   │   ✓ ANDROID API HERE                   │
│             │  │   └→ Result<Boolean, LocationError>        │
│             │  │                                            │
│             │  │ Returns: Result.Success(true)              │
│             │  │                                            │
└─────────────┘  └──────────────┬─────────────────────────────┘
                                │
                                ▼
                     ┌────────────────────────────────────────┐
                     │   DATA LAYER                           │
                     │                                        │
                     │LocationRepository.getCurrentUserLocation()
                     │                                        │
                     │← RemoteLocationDataSource              │
                     │  ├→ LocationFetcher.getLastKnownLoc()  │
                     │  │   ├→ FusedLocationProviderClient    │
                     │  │   │   .lastLocation ✓ ANDROID HERE  │
                     │  │   └→ Location object                │
                     │  └→ Result.Success(UserLocation(...))  │
                     │                                        │
                     └────────────────┬──────────────────────┘
                                      │
                                      ▼
                     ┌────────────────────────────────────────┐
                     │   DOMAIN LAYER                         │
                     │                                        │
                     │ emit(Result.Success(UserLocation))     │
                     │                                        │
                     └────────────────┬──────────────────────┘
                                      │
                                      ▼
                     ┌────────────────────────────────────────┐
                     │   PRESENTATION LAYER                   │
                     │                                        │
                     │ ViewModel receives Result.Success      │
                     │                                        │
                     │ onAction(OnUserLocationFetched(latLng))│
                     │                                        │
                     │ _uiState.update {                      │
                     │   it.copy(                             │
                     │     userLatLng = latLng,               │
                     │     isLoading = false                  │
                     │   )                                    │
                     │ }                                      │
                     │                                        │
                     │ sendUiEvent(AnimateCameraPosition(...))│
                     │                                        │
                     └────────────────┬──────────────────────┘
                                      │
                                      ▼
                     ┌────────────────────────────────────────┐
                     │   PRESENTATION LAYER                   │
                     │                                        │
                     │ DiscoverScreen collects event          │
                     │                                        │
                     │ cameraPositionState.animate(...)       │
                     │                                        │
                     │ markerState.position = latLng          │
                     │                                        │
                     │ Camera animates to user location       │
                     │                                        │
                     │ ✅ SUCCESS!                            │
                     │                                        │
                     └────────────────────────────────────────┘
```

---

## Permission Request Flow

```
┌─────────────────────────────────────────────────────────────┐
│  User taps request permission button (first time)           │
└──────────────────┬──────────────────────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────────────────────┐
│  viewModel.requestLocationPermission(isFirstTime = true)    │
└──────────────────┬──────────────────────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────────────────────┐
│  CheckLocationPermissionUseCase()                            │
│  → LocationRepository.hasLocationPermission()                │
│     → LocationPermissionManager.hasLocationPermission()      │
│        → ContextCompat.checkSelfPermission() ✓ DATA LAYER   │
└──────────────────┬──────────────────────────────────────────┘
                   │
        ┌──────────┴──────────┐
        │                     │
    Result.Success(false)     Result.Success(true)
        │                     │
        ▼                     ▼
   Permission Denied      Permission Granted
        │                     │
        ▼                     ▼
   Is first time?         Fetch location
        │
    YES ▼
   Send event:
   LaunchLocationActivityResultLauncher
        │
        ▼
   DiscoverScreen receives event
   launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        │
        ▼
   ┌────────────────────────────────────┐
   │ System permission dialog appears   │
   │                                    │
   │  [Allow] [Deny]                    │
   └────────────────────────────────────┘
        │
        ▼
   User responds...
        │
        ├─ [Allow] → launcher callback (isGranted = true)
        │            viewModel.fetchUserLocation()
        │            ✅ Location fetched
        │
        └─ [Deny] → launcher callback (isGranted = false)
                     viewModel.requestLocationPermission(false)
                     ▼
                     CheckLocationPermissionUseCase()
                     → Still returns false
                     ▼
                     ShouldShowLocationPermissionRationaleUseCase()
                     → LocationRepository.shouldShowLocationPermissionRationale()
                        → LocationPermissionManager.shouldShowPermissionRationale()
                           → ActivityCompat.shouldShowRequestPermissionRationale()
                              ✓ DATA LAYER
                     ▼
                     Result.Success(true) → Show explanation dialog
                     OR
                     Result.Success(false) → Show settings dialog
```

---

## Dependency Injection Tree

```
App Module (in :app)
│
├─ includes(featureMapPresentationModule)
│  │
│  ├─ includes(featureMapDataModule)
│  │  │
│  │  ├─ single: Context (androidApplication())
│  │  │
│  │  ├─ single: LocationFetcher
│  │  │  └─ depends on: Context
│  │  │
│  │  ├─ single: LocationPermissionManager
│  │  │  └─ depends on: Context
│  │  │
│  │  ├─ factory: RemoteLocationDataSource
│  │  │  └─ depends on: LocationFetcher
│  │  │
│  │  ├─ single: LocalLocationDataSource
│  │  │  └─ no dependencies
│  │  │
│  │  └─ single<LocationRepository>: LocationRepositoryImpl
│  │     ├─ depends on: RemoteLocationDataSource
│  │     ├─ depends on: LocalLocationDataSource
│  │     └─ depends on: LocationPermissionManager
│  │
│  ├─ factory: GetUserLocationUseCase
│  │  └─ depends on: LocationRepository
│  │
│  ├─ factory: CheckLocationPermissionUseCase
│  │  └─ depends on: LocationRepository
│  │
│  ├─ factory: ShouldShowLocationPermissionRationaleUseCase
│  │  └─ depends on: LocationRepository
│  │
│  ├─ factory: GetPredefinedLocationsUseCase
│  │  └─ depends on: LocationRepository
│  │
│  └─ viewModel: DiscoverViewModel
│     ├─ depends on: GetUserLocationUseCase
│     ├─ depends on: CheckLocationPermissionUseCase
│     ├─ depends on: ShouldShowLocationPermissionRationaleUseCase
│     └─ depends on: GetPredefinedLocationsUseCase
│
└─ Compose
   └─ DiscoverScreen(viewModel: DiscoverViewModel)
```

---

## Error Handling Flow

```
LocationError (sealed interface)
│
├─ NoPermission
│  └─ Returned when: Permission check fails
│     Handled by: ViewModel → Show permission dialog
│
├─ ServiceUnavailable
│  └─ Returned when: Location service unavailable
│     Handled by: ViewModel → Show error message
│
├─ Timeout
│  └─ Returned when: Location request times out
│     Handled by: ViewModel → Show timeout message
│
└─ Unknown
   └─ Returned when: Unexpected error occurs
      Handled by: ViewModel → Show generic error
```

---

## Component Interaction Matrix

```
                 │ Domain │ Presentation │ Data
─────────────────┼────────┼──────────────┼──────
Domain           │   ✓    │      ✗       │  ✗
Presentation     │   ✓    │      ✓       │  ✗
Data             │   ✗    │      ✗       │  ✓

Legend:
✓ = Can access
✗ = Cannot access

Rules:
- Presentation can only access Domain
- Domain cannot access Presentation or Data
- Data cannot access Presentation or Domain
```


