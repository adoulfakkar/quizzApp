'use strict';

/* Services */

var quizzAppModels = angular.module('quizzAppModels', []);

quizzAppModels.factory('QuizModel', function(QuizServices, $rootScope) {
	function QuizModelFactory () {

		//////////////////////
		//	CONST
		//////////////////////
		
		//////////////////////
		//	PRIVATE VARS
		//////////////////////

		var self = this;
		
		var pageSize = 10;
		
		//////////////////////
		//	PUBLIC VARS
		//////////////////////
		
		this.questions = null;
		this.newQuestion = null;
		this.loading = false;
		this.lastError = null;
		this.count = 0;
		this.currentPage = 0;
		
		//////////////////////
		//	PUBLIC METHODS
		//////////////////////
		
		this.init = function () {
			self.loading = true;
			self.loadPage (0);
		};
		
		this.loadPage = function (pageNumber) {
			self.questions = null;
			
			QuizServices.getAll(pageNumber * pageSize, pageSize).success (function (res) {
				self.questions = res.list;
				self.count = res.count;
				self.currentPage = pageNumber + 1;
				for (var i = 0; i < self.questions.length; ++i) {
					var question = self.questions[i];
					setGoodAnswer (question);
				}
				self.loading = false;
			}).error (function (res) {
				self.loading = false;
			});
		}
		
		this.addQuestion = function (type) {
			self.newQuestion = {
				id:null,
				title: "",
        		type: type,
				information:"",
        		isGood: null,
        		updateDate: null
			};
			
			var answers = [{
				id:null,
				text: null,
				imageUrl: null,
				good: false
			},{
				id:null,
				text: null,
				imageUrl: null,
				good: false
			},{
				id:null,
				text: null,
				imageUrl: null,
				good: false
			},{
				id:null,
				text: null,
				imageUrl: null,
				good: false
			}];
		
			switch (type) {
				case 0:
					self.newQuestion.answers = answers;
					break;
				case 1:
					self.newQuestion.answers = [];
					break;
				case 2:
					self.newQuestion.answers = answers;
					break;
				case 3:
					self.newQuestion.answers = [answers[0]];
					break;
			}
			
		};
		
		this.commitNewQuestion = function () {
			this.loading = true;
			normalizeQuestion (self.newQuestion, function (hasError) {
				if (hasError) {
					self.loading = false;
				}
				else {
					QuizServices.addQuestion (self.newQuestion).success (function (res) {
						self.newQuestion = null;
						self.questions.push (res);
						setGoodAnswer (res);
						self.loading = false;
					}).error (function (res) {
						self.loading = false;
					});
				}
			});
		};

		this.deleteNewQuestion = function () {
			self.newQuestion = null;
		};
		
		this.deleteQuestion = function (question) {
			self.loading = true;
			QuizServices.deleteQuestion (question).success (function (res) {
				var idx = self.questions.indexOf (question);
				if (idx != -1) {
					self.questions.splice (idx, 1);
				}
				self.loading = false;
			}).error (function (res) {
				self.loading = false;
			});
		};
		
		this.updateQuestion = function (question) {
			self.loading = true;
			normalizeQuestion (question, function (hasError) {
				if (hasError) {
					self.loading = false;
				}
				else {
					QuizServices.updateQuestion (question).success (function (res) {
						setGoodAnswer (question);
						self.loading = false;
					}).error (function (res) {
						self.loading = false;
					});
				}
			});
		}
		
		//////////////////////
		//	PRIVATE METHODS
		//////////////////////
		function normalizeQuestion (question, callback) {
			if (question.answers && question.goodAnswer) {
				for (var j = 0; j < question.answers.length; ++j) {
					var answer = question.answers[j];
					answer.good = question.goodAnswer.id == answer.id;
				}
			}
			question.goodAnswer = undefined;
			delete question.goodAnswer;
			if (question.type == 2) {
				uploadImages (question, 0, callback);
			}
			else {
				callback ();
			}
		}
		
		function uploadImages (question, currentAnswer, callback) {
			if (currentAnswer < question.answers.length) {
				var answer = question.answers[currentAnswer];
				if (answer.fileUpload) {
					QuizServices.uploadImage (answer.fileUpload).success (function (res) {
						answer.imageUrl = res.path;
						delete answer.fileUpload;
						uploadImages (question, currentAnswer + 1, callback);
					}).error (function (error) {
						callback (true);
					});
				}
				else {
					uploadImages (question, currentAnswer + 1, callback);
				}
			}
			else {
				callback ();
			}
		}
		
		function setGoodAnswer (question) {
			if (question.answers) {
				for (var j = 0; j < question.answers.length; ++j) {
					var answer = question.answers[j];
					if (answer.good) {
						question.goodAnswer = answer;
						break;
					}
				}
			}
		}
		
		//////////////////////
		//	INIT
		//////////////////////

	}
	
	return new QuizModelFactory ();
});


quizzAppModels.factory('NewsModel', function(NewsServices, $rootScope) {
	function NewsModelFactory () {

		//////////////////////
		//	CONST
		//////////////////////
		
		//////////////////////
		//	PRIVATE VARS
		//////////////////////

		var self = this;
		
		var pageSize = 10;
		
		//////////////////////
		//	PUBLIC VARS
		//////////////////////
		
		this.news = null;
		this.newNews = null;
		this.loading = false;
		this.lastError = null;
		this.count = 0;
		this.currentPage = 0;
		
		//////////////////////
		//	PUBLIC METHODS
		//////////////////////
		
		this.init = function () {
			self.loading = true;
			self.news = null;
			self.loadPage (0);
		};
		
		this.loadPage = function (pageNumber) {
			NewsServices.getAll(pageNumber * pageSize, pageSize).success (function (res) {
				self.news = res.list;
				self.count = res.count;
				self.currentPage = pageNumber + 1;
				self.loading = false;
			}).error (function (res) {
				self.loading = false;
			});
		}
		
		this.addNews = function () {
			self.newNews = {
				id:null,
				title: null,
        		description: null,
				updateDate:null,
        		image: null
			};
		};
		
		this.commitNewNews = function () {
			this.loading = true;
			normalizeNews (self.newNews, function (hasError) {
				if (hasError) {
					self.loading = false;
				}
				else {
					NewsServices.addNews (self.newNews).success (function (res) {
						self.newNews = null;
						self.news.push (res);
						self.loading = false;
					}).error (function (res) {
						self.loading = false;
					});
				}
			});
		};

		this.deleteNewNews = function () {
			self.newNews = null;
		};
		
		this.deleteNews = function (news) {
			self.loading = true;
			NewsServices.deleteNews (news).success (function (res) {
				var idx = self.news.indexOf (news);
				if (idx != -1) {
					self.news.splice (idx, 1);
				}
				self.loading = false;
			}).error (function (res) {
				self.loading = false;
			});
		};
		
		this.updateNews = function (news) {
			self.loading = true;
			normalizeNews (news, function (hasError) {
				if (hasError) {
					self.loading = false;
				}
				else {
					NewsServices.updateNews (news).success (function (res) {
						self.loading = false;
					}).error (function (res) {
						self.loading = false;
					});
				}
			});
		}
		
		//////////////////////
		//	PRIVATE METHODS
		//////////////////////
		function normalizeNews (news, callback) {
			if (news.fileUpload) {
				NewsServices.uploadImage (news.fileUpload).success (function (res) {
					news.image = res.path;
					delete news.fileUpload;
					callback ();
				}).error (function (error) {
					callback (true);
				});
			}
			else {
				callback ();
			}
		}
		
		//////////////////////
		//	INIT
		//////////////////////

	}
	
	return new NewsModelFactory ();
});

quizzAppModels.factory("UserModel", function(UserServices, $rootScope, $http) {
	function UserModelFactory () {

		//////////////////////
		//	CONST
		//////////////////////
		
		//////////////////////
		//	PRIVATE VARS
		//////////////////////

		var self = this;
		
		var pageSize = 10;
		
		//////////////////////
		//	PUBLIC VARS
		//////////////////////
		
		this.users = null;
		this.newUser = null;
		this.loading = false;
		this.lastError = null;
		this.count = 0;
		this.currentPage = 0;
		this.token = null;
		
		//////////////////////
		//	PUBLIC METHODS
		//////////////////////
		
		this.login = function (username, password) {
			UserServices.login (username, password).success (function (res) {
				self.token = res.token;
				$http.defaults.headers.common.token = res.token;
				$rootScope.$broadcast ("userLoggued");
			}).error (function () {
				alert ("Erreur lors de l'authentification");
			});
		};
		
		this.init = function () {
			self.loading = true;
			self.users = null;
			self.loadPage (0);
		};
		
		this.loadPage = function (pageNumber) {
			UserServices.getAll(pageNumber * pageSize, pageSize).success (function (res) {
				self.users = res.list;
				self.count = res.count;
				self.currentPage = pageNumber + 1;
				self.loading = false;
			}).error (function (res) {
				self.loading = false;
			});
		}
		
		this.addUser = function () {
		};
		
		this.commitNewUser = function () {
			this.loading = true;
			normalizeUser (self.newUser, function (hasError) {
				if (hasError) {
					self.loading = false;
				}
				else {
					UserServices.addUser (self.newUser).success (function (res) {
						self.newUser = null;
						self.users.push (res);
						self.loading = false;
					}).error (function (res) {
						self.loading = false;
					});
				}
			});
		};

		this.deleteNewUser = function () {
			self.newUser = null;
		};
		
		this.deleteUser = function (user) {
			self.loading = true;
			UserServices.deleteUser (user).success (function (res) {
				var idx = self.users.indexOf (user);
				if (idx != -1) {
					self.users.splice (idx, 1);
				}
				self.loading = false;
			}).error (function (res) {
				self.loading = false;
			});
		};
		
		this.updateUser = function (user) {
			self.loading = true;
			normalizeUser (user, function (hasError) {
				if (hasError) {
					self.loading = false;
				}
				else {
					UserServices.updateUser (user).success (function (res) {
						self.loading = false;
					}).error (function (res) {
						self.loading = false;
					});
				}
			});
		}
		
		//////////////////////
		//	PRIVATE METHODS
		//////////////////////
		function normalizeUser (user, callback) {
			if (user.fileUpload) {
				UserServices.uploadImage (user.fileUpload).success (function (res) {
					user.image = res.path;
					delete user.fileUpload;
					callback ();
				}).error (function (error) {
					callback (true);
				});
			}
			else {
				callback ();
			}
		}
		
		//////////////////////
		//	INIT
		//////////////////////

	}
	
	return new UserModelFactory ();
});

quizzAppModels.factory("WebSerieModel", function(WebSerieServices, $rootScope) {
	function WebSerieModelFactory () {

		//////////////////////
		//	CONST
		//////////////////////
		
		//////////////////////
		//	PRIVATE VARS
		//////////////////////

		var self = this;
		
		var pageSize = 10;
		
		//////////////////////
		//	PUBLIC VARS
		//////////////////////
		
		this.webSeries = null;
		this.newWebSerie = null;
		this.loading = false;
		this.lastError = null;
		this.count = 0;
		this.currentPage = 0;
		
		//////////////////////
		//	PUBLIC METHODS
		//////////////////////
		
		this.init = function () {
			self.loading = true;
			self.webSeries = null;
			self.loadPage (0);
		};
		
		this.loadPage = function (pageNumber) {
			WebSerieServices.getAll(pageNumber * pageSize, pageSize).success (function (res) {
				self.webSeries = res.list;
				self.count = res.count;
				self.currentPage = pageNumber + 1;
				self.loading = false;
			}).error (function (res) {
				self.loading = false;
			});
		}
		
		this.addWebSerie = function () {
			self.newWebSerie = {
				id:null,
				title: null,
        		description: null,
				updateDate:null,
        		fileUrl: null
			};
		};
		
		this.commitNewWebSerie = function () {
			this.loading = true;
			normalize(self.newWebSerie, function () {
				WebSerieServices.addWebSerie (self.newWebSerie).success (function (res) {
					self.newWebSerie = null;
					self.webSeries.push (res);
					self.loading = false;
				}).error (function (res) {
					self.loading = false;
				});
			});
		};

		this.deleteNewWebSerie = function () {
			self.newWebSerie = null;
		};
		
		this.deleteWebSerie = function (webSerie) {
			self.loading = true;
			WebSerieServices.deleteWebSerie (webSerie).success (function (res) {
				if (hasError) {
					self.loading = false;
					return;
				}
				var idx = self.webSeries.indexOf (webSerie);
				if (idx != -1) {
					self.webSeries.splice (idx, 1);
				}
				self.loading = false;
			}).error (function (res) {
				self.loading = false;
			});
		};
		
		this.updateWebSerie = function (webSerie) {
			self.loading = true;
			normalize(webSerie, function (hasError) {
				if (hasError) {
					self.loading = false;
					return;
				}
				WebSerieServices.updateWebSerie (webSerie).success (function (res) {
					self.loading = false;
				}).error (function (res) {
					self.loading = false;
				});
			});
		}
		
		//////////////////////
		//	PRIVATE METHODS
		//////////////////////
		function normalize (webSerie, callback) {
			if (webSerie.fileUpload) {
				WebSerieServices.uploadImage (webSerie.fileUpload).success (function (res) {
					webSerie.previewUrl = res.path;
					delete webSerie.fileUpload;
					callback ();
				}).error (function (error) {
					callback (true);
				});
			}
			else {
				callback ();
			}
		}
		
		//////////////////////
		//	INIT
		//////////////////////

	}
	
	return new WebSerieModelFactory ();
});

quizzAppModels.factory("GoodPracticeModel", function(GoodPracticeServices, $rootScope) {
	function GoodPracticeModelFactory () {

		//////////////////////
		//	CONST
		//////////////////////
		
		//////////////////////
		//	PRIVATE VARS
		//////////////////////

		var self = this;
		
		var pageSize = 10;
		
		//////////////////////
		//	PUBLIC VARS
		//////////////////////
		
		this.goodPractices = null;
		this.newGoodPractice = null;
		this.loading = false;
		this.lastError = null;
		this.count = 0;
		this.currentPage = 0;
		
		//////////////////////
		//	PUBLIC METHODS
		//////////////////////
		
		this.init = function () {
			self.loading = true;
			self.goodPractices = null;
			self.loadPage (0);
		};
		
		this.loadPage = function (pageNumber) {
			GoodPracticeServices.getAll(pageNumber * pageSize, pageSize).success (function (res) {
				self.goodPractices = res.list;
				self.count = res.count;
				self.currentPage = pageNumber + 1;
				self.loading = false;
			}).error (function (res) {
				self.loading = false;
			});
		}
		
		this.addGoodPractice = function () {
			self.newGoodPractice = {
				id:null,
				title: null,
				type:1,
				updateDate:null,
        		fileUrl: null
			};
		};
		
		this.commitNewGoodPractice = function () {
			this.loading = true;
			normalizeGoodPractice (self.newGoodPractice, function (hasError) {
				if (hasError) {
					self.loading = false;
				}
				else {
					GoodPracticeServices.addGoodPractice (self.newGoodPractice).success (function (res) {
						self.newGoodPractice = null;
						self.goodPractices.push (res);
						self.loading = false;
					}).error (function (res) {
						self.loading = false;
					});
				}
			});
		};

		this.deleteNewGoodPractice = function () {
			self.newGoodPractice = null;
		};
		
		this.deleteGoodPractice = function (goodPractice) {
			self.loading = true;
			GoodPracticeServices.deleteGoodPractice (goodPractice).success (function (res) {
				var idx = self.goodPractices.indexOf (goodPractice);
				if (idx != -1) {
					self.goodPractices.splice (idx, 1);
				}
				self.loading = false;
			}).error (function (res) {
				self.loading = false;
			});
		};
		
		this.updateGoodPractice = function (goodPractice) {
			self.loading = true;
			normalizeGoodPractice (goodPractice, function (hasError) {
				if (hasError) {
					self.loading = false;
				}
				else {
					GoodPracticeServices.updateGoodPractice (goodPractice).success (function (res) {
						self.loading = false;
					}).error (function (res) {
						self.loading = false;
					});
				}
			});
		}
		
		//////////////////////
		//	PRIVATE METHODS
		//////////////////////
		
		function uploadSuccess (goodPractice, res, callback) {
			goodPractice.fileUrl = res.path;
			delete goodPractice.fileUpload;
			delete goodPractice.imageUpload;
			callback ();
		}
		
		function normalizeGoodPractice (goodPractice, callback) {
			if (goodPractice.type != 1 && goodPractice.fileUpload) {
				GoodPracticeServices.uploadFile (goodPractice.fileUpload).success (function (res) {
					uploadSuccess(goodPractice, res, callback);
				}).error (function (error) {
					callback (true);
				});
			}
			else if (goodPractice.type == 1 && goodPractice.imageUpload) {
				GoodPracticeServices.uploadImage (goodPractice.imageUpload).success (function (res) {
					uploadSuccess(goodPractice, res, callback);
				}).error (function (error) {
					callback (true);
				});
			}
			else {
				callback ();
			}
		}
		
		//////////////////////
		//	INIT
		//////////////////////

	}
	
	return new GoodPracticeModelFactory ();
});