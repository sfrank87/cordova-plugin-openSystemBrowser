
(function() {
    var exec = require('cordova/exec');
    var urlutil = require('cordova/urlutil');

    module.exports = function(strUrl) {
        strUrl = urlutil.makeAbsolute(strUrl);
        exec(null, null, "opensystembrowser", "open", [strUrl]);
    };
})();
