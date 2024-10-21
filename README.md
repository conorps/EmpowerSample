A simple Android app with a list-detail flow
- Uses `State` data classes to represent each of the screens
- `Actions` are generated by user interaction and are handled by the `viewModel`
- `State` and `Effects` are emitted by the viewModel and handled by the `activities`
- Repository layer fetches locally store data and returns it to the `viewModel`
- `ViewModel` exposes the data to the UI through the `stateFlow`
