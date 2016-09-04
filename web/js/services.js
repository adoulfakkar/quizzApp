'use strict';

/* Services */

var quizzAppServices = angular.module('quizzAppServices', []);

quizzAppServices.service('AbstractServices', function($http, $rootScope) {
	this.send = function (obj) {
		return $http(obj).error (function (error, status) {
			if (status == 401) {
				$rootScope.$broadcast("badUserRights");
			}
			else {
				alert ("Une erreur est survenue");
			}
		});
	}
});

quizzAppServices.service('QuizServices', function(AbstractServices, SERVER_URL) {

	this.getAll = function (from, size) {
		return AbstractServices.send ({
			method:"GET",
			params: {
				from:from,
				size:size
			},
			url:SERVER_URL + "rest/question/"
		})
	};
	
	this.deleteQuestion = function (question) {
		return AbstractServices.send ({
			method:"DELETE",
			url:SERVER_URL + "rest/question/" + question.id
		})
	};
	
	this.addQuestion = function (question) {
		return AbstractServices.send ({
			method:"POST",
			data:question,
			header: {
				"Content-Type":"application/json"
			},
			url:SERVER_URL + "rest/question/"
		})
	};
	
	this.updateQuestion = function (question) {
		return AbstractServices.send ({
			method:"PUT",
			data:question,
			header: {
				"Content-Type":"application/json"
			},
			url:SERVER_URL + "rest/question/"
		})
	};
	
	this.uploadImage = function (file) {
		var fd = new FormData();
        fd.append("image", file);
		return AbstractServices.send ({
			method:"POST",
			data:fd,
			headers: {'Content-Type': undefined },
			transformRequest: angular.identity,
			url:SERVER_URL + "rest/image/upload"
		})
	};
});

quizzAppServices.service('NewsServices', function(AbstractServices, SERVER_URL) {

	this.getAll = function (from, size) {
		return AbstractServices.send ({
			method:"GET",
			params: {
				from:from,
				size:size
			},
			url:SERVER_URL + "rest/news/"
		});
	};
	
	this.deleteNews = function (news) {
		return AbstractServices.send ({
			method:"DELETE",
			url:SERVER_URL + "rest/news/" + news.id
		});
	};
	
	this.addNews = function (news) {
		return AbstractServices.send ({
			method:"POST",
			data:news,
			header: {
				"Content-Type":"application/json"
			},
			url:SERVER_URL + "rest/news/"
		});
	};
	
	this.updateNews = function (news) {
		return AbstractServices.send ({
			method:"PUT",
			data:news,
			header: {
				"Content-Type":"application/json"
			},
			url:SERVER_URL + "rest/news/"
		});
	};
	
	this.uploadImage = function (file) {
		var fd = new FormData();
        fd.append("image", file);
		return AbstractServices.send ({
			method:"POST",
			data:fd,
			headers: {'Content-Type': undefined },
			transformRequest: angular.identity,
			url:SERVER_URL + "rest/image/upload"
		});
	}
});

quizzAppServices.service('UserServices', function(AbstractServices, SERVER_URL) {

	this.login = function (username, password) {
		return AbstractServices.send ({
			method:"POST",
			data: {
				nickname:username,
				password:password
			},
			url:SERVER_URL + "rest/user/login"
		});
	};
	
	this.getToken = function (username, password) {
		return AbstractServices.send ({
			method:"POST",
			data: {
				nickname:username,
				password:password
			},
			url:SERVER_URL + "rest/user/download/login"
		});
	};
	
	
	this.getAll = function (from, size) {
		return AbstractServices.send ({
			method:"GET",
			params: {
				from:from,
				size:size
			},
			url:SERVER_URL + "rest/user/"
		});
	};
	
	this.deleteNews = function (user) {
		return AbstractServices.send ({
			method:"DELETE",
			url:SERVER_URL + "rest/user/" + user.id
		});
	};
	
	this.addNews = function (user) {
		return AbstractServices.send ({
			method:"POST",
			data:user,
			header: {
				"Content-Type":"application/json"
			},
			url:SERVER_URL + "rest/user/"
		});
	};
	
	this.updateNews = function (user) {
		return AbstractServices.send ({
			method:"PUT",
			data:user,
			header: {
				"Content-Type":"application/json"
			},
			url:SERVER_URL + "rest/user/"
		})
	};
	
});

quizzAppServices.service('WebSerieServices', function(AbstractServices, SERVER_URL) {

	this.getAll = function (from, size) {
		return AbstractServices.send ({
			method:"GET",
			params: {
				from:from,
				size:size
			},
			url:SERVER_URL + "rest/webSerie/"
		})
	};
	
	this.deleteWebSerie = function (webSerie) {
		return AbstractServices.send ({
			method:"DELETE",
			url:SERVER_URL + "rest/webSerie/" + webSerie.id
		})
	};
	
	this.addWebSerie = function (webSerie) {
		return AbstractServices.send ({
			method:"POST",
			data:webSerie,
			header: {
				"Content-Type":"application/json"
			},
			url:SERVER_URL + "rest/webSerie/"
		})
	};
	
	this.updateWebSerie = function (webSerie) {
		return AbstractServices.send ({
			method:"PUT",
			data:webSerie,
			header: {
				"Content-Type":"application/json"
			},
			url:SERVER_URL + "rest/webSerie/"
		})
	};
	
	this.uploadImage = function (file) {
		var fd = new FormData();
        fd.append("image", file);
		return AbstractServices.send ({
			method:"POST",
			data:fd,
			headers: {'Content-Type': undefined },
			transformRequest: angular.identity,
			url:SERVER_URL + "rest/image/upload"
		})
	}
});

quizzAppServices.service('GoodPracticeServices', function(AbstractServices, SERVER_URL) {

	this.getAll = function (from, size) {
		return AbstractServices.send ({
			method:"GET",
			params: {
				from:from,
				size:size
			},
			url:SERVER_URL + "rest/goodPractice/"
		})
	};
	
	this.deleteGoodPractice = function (goodPractice) {
		return AbstractServices.send ({
			method:"DELETE",
			url:SERVER_URL + "rest/goodPractice/" + goodPractice.id
		})
	};
	
	this.addGoodPractice = function (goodPractice) {
		return AbstractServices.send ({
			method:"POST",
			data:goodPractice,
			header: {
				"Content-Type":"application/json"
			},
			url:SERVER_URL + "rest/goodPractice/"
		})
	};
	
	this.updateGoodPractice = function (goodPractice) {
		return AbstractServices.send ({
			method:"PUT",
			data:goodPractice,
			header: {
				"Content-Type":"application/json"
			},
			url:SERVER_URL + "rest/goodPractice/"
		})
	};
	
	this.uploadImage = function (file) {
		var fd = new FormData();
        fd.append("image", file);
		return AbstractServices.send ({
			method:"POST",
			data:fd,
			headers: {'Content-Type': undefined },
			transformRequest: angular.identity,
			url:SERVER_URL + "rest/image/upload"
		})
	}
	
	this.uploadFile = function (file) {
		var fd = new FormData();
        fd.append("file", file);
		return AbstractServices.send ({
			method:"POST",
			data:fd,
			headers: {'Content-Type': undefined },
			transformRequest: angular.identity,
			url:SERVER_URL + "rest/file/upload"
		})
	}
});
