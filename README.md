# APPForeground
The plugin uses android foreground technology to raise the priority of the program to the second (the app currently used by the user is the first). Let our APP run longer

## Installing
### cordova
`$ cordova plugin add cordova-plugin-appforeground`

## API
### Methods
- startService  
```js
    cordova.plugins.APPForeground.startService({
        title:'foreground notifications title',
        text:"Don't kill me in the system..."
    });
```
    
- stopService   
```js
    cordova.plugins.APPForeground.stopService();
```

- setting   
```js
    cordova.plugins.APPForeground.goSetting();
```
**After launching the service, our app meets the requirements of google Android to keep the background running for a long time, but in the android camp, each mobile phone manufacturer customizes its own operating system based on Android. The customized operating system will be cleared by default in the power management. APP to achieve power saving purposes. So we have to set our app as a whitelist in "Settings" - "Battery Management", you can use the power without limit... so that our app can stay in the background for a long time~**