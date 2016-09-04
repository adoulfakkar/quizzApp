var quizzAppApp = angular.module("quizzAppApp", ["ui.bootstrap", "ngRoute", "quizzAppControllers", "quizzAppServices", "quizzAppModels"]);


//====================================//
//==	CONSTANTS
//====================================//

quizzAppApp.constant ("SERVER_URL", "/quizzAppQuizServerPreprod/")
//quizzAppApp.constant ("SERVER_URL", "//localhost:8080/quizzAppQuizServer/")

//====================================//
//==	FACTORIES
//====================================//

quizzAppApp.config(["$routeProvider", "$httpProvider", function($routeProvider, $httpProvider) {
	$httpProvider.defaults.headers.common["Content-Type"] = "charset='UTF-8'";
	$routeProvider.when('/login', {
    	templateUrl: 'partials/login.html',
    	controller: 'LoginCtrl'
    }).when('/quiz', {
    	templateUrl: 'partials/quiz.html',
    	controller: 'QuizCtrl'
    }).when('/news', {
    	templateUrl: 'partials/news.html',
    	controller: 'NewsCtrl'
    }).when('/users', {
    	templateUrl: 'partials/users.html',
    	controller: 'UserCtrl'
    }).when('/webserie', {
    	templateUrl: 'partials/webserie.html',
    	controller: 'WebSerieCtrl'
    }).when('/practice', {
    	templateUrl: 'partials/goodpractice.html',
    	controller: 'GoodPracticeCtrl'
    }).otherwise({
		redirectTo:"/quiz"
	});
}]);


//====================================//
//==	CONFIGURATIONS
//====================================//

//====================================//
//==	DIRECTIVES
//====================================//

quizzAppApp.directive ("uploadPreviewFile", function ($q) {
	return {
		require:"ngModel",
		scope: {
			imgSrc:"="
		},
		template:"<input type='file' /><img class='answerImg' ng-src='{{imgSrc}}' /><span class='error'></span>",
		link: function ($scope, element, attrs, ngModel) {
			var inputFile = angular.element (element.children()[0]);
			var imgEl = angular.element (element.children()[1]);
			var errorEl = angular.element (element.children()[2]);
			
			ngModel.$validators.fileSize = function(modelValue, viewValue) {
				if (ngModel.$isEmpty(modelValue)) {
					return true;
				}
				var file = viewValue;
				if (file.size > 1048576) {
					errorEl.text("Fichier trog grand (max 1mo)");
					return false;
				}
				return true;
		 	}
			ngModel.$asyncValidators.imageSize = function(modelValue, viewValue) {

				if (ngModel.$isEmpty(modelValue)) {
					// consider empty model valid
				  	return $q.when();
				}

				var def = $q.defer();
				
				var file = viewValue;
				var reader = new FileReader();
				var img = new Image ();
				img.onload = function () {
					if (this.width > 500 || this.height > 500) {
						errorEl.text("Fichier trog grand (max 500px x 500px)");
						def.reject();
						return;
					}
					def.resolve ();
				}
				
				reader.onload = function (e) {
					imgEl.attr('src', e.target.result);
					img.src = e.target.result;
				}
				reader.readAsDataURL(viewValue);
				
				return def.promise;
			  };
			
			inputFile.on ("change", function () {
				errorEl.text("");
				imgEl.attr('src', null);
				var file = this.files[0];
				if (this.files && this.files[0]) {
					ngModel.$setViewValue (file);
				}
				else {
					ngModel.$setViewValue (null);
				}
		  	});
		}
	};
});

quizzAppApp.directive ("uploadFile", function () {
	return {
		require:"ngModel",
		link: function ($scope, element, attrs, ngModel) {
			element.on ("change", function () {
				if (this.files && this.files[0]) {
					ngModel.$setViewValue (this.files[0]);
				}
				else {
					ngModel.$setViewValue (null);
				}
		  	});
		}
	};
});

//====================================//
//==	FILTERS
//====================================//

