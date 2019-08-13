# Composite Plugin Example
The example should provide insights on how to write a composite gradle plugin.
A composite plugin needs another plugin to be applied before adding it's own 
logic to the the build-script.

## How to use the Plugin
```
gradle integrationTest
```

### Dependencies
```
integrationTestImplementation group: 'junit', name: 'junit', version: '4.4'
```
