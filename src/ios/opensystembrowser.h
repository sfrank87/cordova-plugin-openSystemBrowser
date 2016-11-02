
#import <Cordova/CDVPlugin.h>
#import <Cordova/CDVInvokedUrlCommand.h>

@class opensystembrowser;

@interface opensystembrowser : CDVPlugin {
}

@property (nonatomic, copy) NSString* callbackId;

- (void)open:(CDVInvokedUrlCommand*)command;

@end
