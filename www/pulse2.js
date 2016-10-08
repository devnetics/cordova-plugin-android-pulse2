var exec = require('cordova/exec');

exports.connect = function(successCallback, errorCallback) {
  exec(successCallback, errorCallback, "Pulse2Plugin", "connect", []);
};

exports.init = function(successCallback, errorCallback) {
  exec(successCallback, errorCallback, "Pulse2Plugin", "init", []);
}