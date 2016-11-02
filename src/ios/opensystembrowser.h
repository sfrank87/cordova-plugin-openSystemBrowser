
#import <Cordova/CDVPlugin.h>
#import <Cordova/CDVInvokedUrlCommand.h>

@class opensystembrowser;

@interface opensystembrowser : CDVPlugin {
}

- (void)open:(CDVInvokedUrlCommand*)command;

@end
