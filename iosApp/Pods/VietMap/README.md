### Build xcframework

```bash
cd platform/iOS
make xcframework BUILDTYPE=Release
```

### Copy __VietMap.xcframework__ to this project

### Change version in VietMap.podspec file

### Push & create new tag match with the changed version
 
### Github action will release new version to cocoapod automatically

# Below guide is old document and only use for manual release. This project is automatically release with github action CICD and don't necessary to follow below work flow

### Run below terminal 

- Check if you have authentication to cocoapods
```bash
pod trunk me`
```
- cd to `Podspec/Vietmap`
Validate podspec:
```bash
cd Podspec/VietMap && pod spec lint --allow-warnings
```
Push library
```bash
pod trunk push --allow-warnings
```

### Follow below url to read more info
https://guides.cocoapods.org/making/getting-setup-with-trunk.html