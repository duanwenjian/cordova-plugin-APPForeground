/********* APPForeground.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>

@interface APPForeground : CDVPlugin {
  // Member variables go here.
}

- (void)coolMethod:(CDVInvokedUrlCommand*)command;
- (void)startService:(CDVInvokedUrlCommand*)command;
- (void)stopService:(CDVInvokedUrlCommand*)command;
- (void)goSetting:(CDVInvokedUrlCommand*)command;
- (void)iOSFuncBack:(CDVInvokedUrlCommand*)command;
@end

@implementation APPForeground

- (void)coolMethod:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    NSString* echo = [command.arguments objectAtIndex:0];

    if (echo != nil && [echo length] > 0) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:echo];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)startService:(CDVInvokedUrlCommand*)command
{
    [self performSelectorOnMainThread:@selector(iOSFuncBack:) withObject:command waitUntilDone:(NO)];
}

- (void)stopService:(CDVInvokedUrlCommand*)command
{
    [self performSelectorOnMainThread:@selector(iOSFuncBack:) withObject:command waitUntilDone:(NO)];
}

- (void)goSetting:(CDVInvokedUrlCommand*)command
{
    [self performSelectorOnMainThread:@selector(iOSFuncBack:) withObject:command waitUntilDone:(NO)];
}

- (void)iOSFuncBack:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    NSString* echo = @"此插件暂不支持iOS";
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:echo];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

@end
