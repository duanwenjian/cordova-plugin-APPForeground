var exec = require('cordova/exec');

var APPForeground = {
	coolMethod: function (arg0, success, error) {
	    exec(success, error, 'APPForeground', 'coolMethod', [arg0]);
	},
	startService: function(arg0, success, error){
		exec(success, error, 'APPForeground', 'startService', [arg0]);
	},
	stopService:function(success,error){
		exec(success, error, 'APPForeground', 'stopService', []);
	},
	goSetting:function(success,error){
		exec(success, error, 'APPForeground', 'goSetting', []);
	}
};

module.exports = APPForeground;
