
#import "opensystembrowser.h"
#import <Cordova/CDVPluginResult.h>

#pragma mark opensystembrowser

@interface opensystembrowser () {
}
@end

@implementation opensystembrowser

- (void)pluginInitialize
{
}

- (void)open:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult;

    NSString* url = [command argumentAtIndex:0];

    if (url != nil) {
        NSURL* absoluteUrl = [NSURL URLWithString:url];
        [[NSNotificationCenter defaultCenter] postNotification:[NSNotification notificationWithName:CDVPluginHandleOpenURLNotification object:absoluteUrl]];
        [[UIApplication sharedApplication] openURL:absoluteUrl];

        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"incorrect number of arguments"];
    }

    [pluginResult setKeepCallback:[NSNumber numberWithBool:YES]];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

@end
