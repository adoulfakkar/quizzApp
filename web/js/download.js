var quizzAppApp = angular.module("quizzAppApp", ["ui.bootstrap", "ngRoute", "quizzAppServices"]);


//====================================//
//==	CONSTANTS
//====================================//

quizzAppApp.constant ("SERVER_URL", "/quizzAppQuizServerPreprod/")
//quizzAppApp.constant ("SERVER_URL", "//localhost:8080/quizzAppQuizServer/")

//====================================//
//==	FACTORIES
//====================================//

quizzAppApp.controller ("DownloadCtrl", function ($scope, UserServices, SERVER_URL) {
	$scope.connected = false;
	
	$scope.user = {
		nickname: "",
		password: ""
	}
	
	$scope.connect = function () {
		UserServices.getToken ($scope.user.nickname, $scope.user.password).success (function (res) {
			$scope.token = res.token;
			$scope.connected = true;
		}).error (function () {
			alert ("Erreur lors de l'authentification");
		});
	};
	
	$scope.downloadApplication = function () {
		var url = SERVER_URL + "rest/get/application.apk";
		var newWindow = window.open (url + "?token="+$scope.token);
	};
	
});

//====================================//
//==	CONFIGURATIONS
//====================================//

//====================================//
//==	DIRECTIVES
//====================================//

//====================================//
//==	FILTERS
//====================================//

