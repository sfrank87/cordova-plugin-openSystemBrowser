
#import "opensystembrowser.h"
#import <Cordova/CDVPluginResult.h>
#import <Cordova/CDVUserAgentUtil.h>


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

    self.callbackId = command.callbackId;

    if (url != nil) {
        NSURL* absoluteUrl = [NSURL URLWithString:url];
        [self openInSystem:absoluteUrl];

        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"incorrect number of arguments"];
    }

    [pluginResult setKeepCallback:[NSNumber numberWithBool:YES]];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)openInSystem:(NSURL*)url
{
    [[NSNotificationCenter defaultCenter] postNotification:[NSNotification notificationWithName:CDVPluginHandleOpenURLNotification object:url]];
    [[UIApplication sharedApplication] openURL:url];
}

@end
