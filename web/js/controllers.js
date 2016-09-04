var quizzAppControllers = angular.module('quizzAppControllers', []);

quizzAppControllers.controller('quizzAppCtrl', function ($scope, $location, UserModel)
{
	$scope.unauthorized = false;
	
	$scope.userModel = UserModel;
	
	$scope.currentView = $location.path ();

	$scope.$on ("$routeChangeSuccess", function () {
		$scope.currentView = $location.path ();
	});
	
	$scope.$on ("$routeChangeStart", function (event) {
		var path = $location.path ();
		if (path != "/login" && UserModel.token == null) {
			event.preventDefault ();
			$location.path ("/login");
		}
	});
	
	$scope.$on("badUserRights", function () {
		$scope.unauthorized = true;
	});
});

quizzAppControllers.controller('LoginCtrl', function ($scope, $location, UserModel) {
	$scope.$on ("userLoggued", function () {
		$location.path ("/quiz");
	});
	
	$scope.user = {
		nickname:"FRDJETC",
		password:"FRDJETC"
	}
	
	$scope.submitLogin = function () {
		UserModel.login ($scope.user.nickname, $scope.user.password);
	}
});

quizzAppControllers.controller('QuizCtrl', function ($scope, QuizModel) {
	$scope.model = QuizModel;
	
	QuizModel.init ();
	
	$scope.addQuestion = function (type) {
		QuizModel.addQuestion (type);
	};
	
	$scope.commitNewQuestion = function () {
		QuizModel.commitNewQuestion ();
	};

	$scope.deleteNewQuestion = function () {
		QuizModel.deleteNewQuestion ();
	};
	
	$scope.deleteQuestion = function (question) {
		QuizModel.deleteQuestion (question);
	};

	$scope.updateQuestion = function (question) {
		QuizModel.updateQuestion (question);
	};
	
	$scope.loadPage = function (pageNumber) {
		QuizModel.loadPage (pageNumber);
	};
	
});

quizzAppControllers.controller('NewsCtrl', function ($scope, NewsModel) {
	$scope.model = NewsModel;
	
	NewsModel.init ();
	
	$scope.addNews = function () {
		NewsModel.addNews ();
	};
	
	$scope.commitNewNews = function () {
		NewsModel.commitNewNews ();
	};

	$scope.deleteNewNews = function () {
		NewsModel.deleteNewNews ();
	};
	
	$scope.deleteNews = function (news) {
		NewsModel.deleteNews (news);
	};

	$scope.updateNews = function (news) {
		NewsModel.updateNews (news);
	};
	
	$scope.loadPage = function (pageNumber) {
		NewsModel.loadPage (pageNumber);
	};
	
});

quizzAppControllers.controller('UserCtrl', function ($scope, UserModel) {
	$scope.model = UserModel;
	
	UserModel.init ();
	
	$scope.addUser = function () {
		UserModel.addUser ();
	};
	
	$scope.commitNewUser = function () {
		UserModel.commitNewUser ();
	};

	$scope.deleteNewUser = function () {
		UserModel.deleteNewUser ();
	};
	
	$scope.deleteUser = function (user) {
		UserModel.deleteUser (user);
	};

	$scope.updateUser = function (user) {
		UserModel.updateUser (user);
	};
	
	$scope.loadPage = function (pageNumber) {
		UserModel.loadPage (pageNumber);
	};
	
});

quizzAppControllers.controller('WebSerieCtrl', function ($scope, WebSerieModel, SERVER_URL, $http) {
	$scope.model = WebSerieModel;
	
	var wsToYoutube = [];
	
	WebSerieModel.init ();
	
	$scope.addWebSerie = function () {
		WebSerieModel.addWebSerie ();
	};
	
	$scope.commitNewWebSerie = function () {
		WebSerieModel.commitNewWebSerie ();
	};

	$scope.deleteNewWebSerie = function () {
		WebSerieModel.deleteNewWebSerie ();
	};
	
	$scope.deleteWebSerie = function (webSerie) {
		WebSerieModel.deleteWebSerie (webSerie);
	};

	$scope.updateWebSerie = function (webSerie) {
		WebSerieModel.updateWebSerie (webSerie);
	};
	
	$scope.loadPage = function (pageNumber) {
		WebSerieModel.loadPage (pageNumber);
	};
	
	$scope.checkVideo = function (webserie) {
		window.open (SERVER_URL + "rest/get/video.mp4?token=" + $http.defaults.headers.common.token + "&videoName=" + webserie.fileUrl);
	}
	
//	$scope.getPreviewUrl = function (webSerie) {
//		var idx = webSerie.fileUrl.lastIndexOf ("/");
//		var yId = webSerie.fileUrl.substring (idx + 1);
//		wsToYoutube[webSerie.id] = yId;
//	}
//	
//	$scope.getNewPreviewUrl = function (webSerie) {
//		var idx = webSerie.fileUrl.lastIndexOf ("v=");
//		var yId = webSerie.fileUrl.substring (idx + 2);
//		$scope.newYoutube = yId;
//	}
//	
//	$scope.getYoutubeUrl = function (webserie) {
//		if (!webserie.id && $scope.newYoutube) {
//			return "https://www.youtube.com/embed/" + $scope.newYoutube;
//		} else if (wsToYoutube[webserie.id])
//			return "https://www.youtube.com/embed/" + wsToYoutube[webserie.id];
//		return "";
//	}
	
});

quizzAppControllers.controller('GoodPracticeCtrl', function ($scope, GoodPracticeModel) {
	$scope.model = GoodPracticeModel;
	
	GoodPracticeModel.init ();
	
	$scope.addGoodPractice = function () {
		GoodPracticeModel.addGoodPractice ();
	};
	
	$scope.commitNewGoodPractice = function () {
		GoodPracticeModel.commitNewGoodPractice ();
	};

	$scope.deleteNewGoodPractice = function () {
		GoodPracticeModel.deleteNewGoodPractice ();
	};
	
	$scope.deleteGoodPractice = function (goodPractice) {
		GoodPracticeModel.deleteGoodPractice (goodPractice);
	};

	$scope.updateGoodPractice = function (goodPractice) {
		GoodPracticeModel.updateGoodPractice (goodPractice);
	};
	
	$scope.loadPage = function (pageNumber) {
		GoodPracticeModel.loadPage (pageNumber);
	};
	
});