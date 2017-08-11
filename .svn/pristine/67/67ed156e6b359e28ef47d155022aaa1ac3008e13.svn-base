(function(angular) {
	'use strict';
	define(['app'], function (app) {
		app.register.controller('RateController', ["$rootScope", "$scope", "$resource", "$timeout", "$q", function($rootScope, $scope, $resource, $timeout, $q){
			/**============================================接口对象定义部分============================================**/
			var Rate = $resource("api/information/rate/:id", { "id" : "@id"}, {
				init : { method : 'GET',responseType: "json", url : 'api/information/rate/init'},
				validateLevel : {method : 'GET', url : 'api/information/rate/checklevel/:level/:id'},
				getListOfRateLevels : {method : 'GET', isArray : true, url : 'api/information/rate/getListOfRateLevels'},
				get : { method : 'GET'},
				save : { method : 'PUT'},
				remove : { method : 'DELETE'}
			});
			/**============================================表单查询定义部分============================================**/
			$scope.searchForm = {
				data:{},
				submit: function(){
					$scope.searchForm.data = angular.copy($scope.searchForm.data);
				}
			};
			/**============================================页面方法定义部分============================================**/
			$scope.changeToNum = function(level){
				if (isNaN(level)) {
					$scope.searchForm.data.stageLevel = undefined;
				}
			};
			$scope.info = function(target){
				$scope.dialog.editabled = false;
				$scope.dialog.increased = false;
				$scope.dialog.title = "费率信息详情";
				$scope.dialog.data = angular.copy(target);
				$scope.dialog.show();
			};
			$scope.add = function(parentId){
				$scope.dialog.editabled = true;
				$scope.dialog.increased = true;
				$scope.dialog.title = "新增费率信息";
				$scope.dialog.data = {};
				$scope.dialog.show();
				
				$scope.dialog.submit = function(event){
					event.preventDefault();
					Rate.save($scope.dialog.data,function(response){
						$scope.pagination.unshift(response);
                    });
					$scope.dialog.close();
				};
			}
			$scope.edit = function(target){
				$scope.dialog.editabled = true;
				$scope.dialog.increased = false;
				$scope.dialog.title = "修改费率信息";
				$scope.dialog.data = angular.copy(target);
				$scope.dialog.show();
				
				$scope.dialog.submit = function(event){
					event.preventDefault();
					Rate.save($scope.dialog.data, function(response){
						$scope.pagination.refresh(response);
                    }, function(response){
                    	$scope.$confrim.init(response).show();
                    });
					$scope.dialog.close();
				};
			}
			$scope.remove = function(target){
				$scope.$confrim.init({title:'警告',type:'warning',content : '是否要删除该费率信息?',callback:function(){
					Rate.remove({"id":target.id},function(response){
						if (response.state == undefined) {
							$scope.pagination.remove(target);
						} else {
							$scope.$confrim.init({title:'警告',type:'warning',content : response.message}).show();
						}
                    });
				}}).show();
			};
			/**============================================页面元素初始化部分============================================**/
			/**============================================对话框元素初始化部分============================================**/
			$scope.$on('dialog-completed', function(e, dialog, element){
				dialog.timeToNum = function(time){
					if (isNaN(time)) {
						dialog.data.stageTime = undefined;
					}
				};
				dialog.priceToNum = function(price){
					if (isNaN(price)) {
						dialog.data.stagePrice = undefined;
					}
				};
				dialog.levelToNum = function(level){
					if (isNaN(level)) {
						dialog.data.stageLevel = undefined;
					}
				};
				dialog.validateLevel = function(level){
					return $q(function(resolve, reject) {
						if(level === undefined){
							reject();
						}else if(level !== dialog.data.stageLevel){
							Rate.validateLevel({"id": dialog.data.id, "level": level}, function(response){
								response.state === true ? reject(): resolve();
							});
						}else{
							resolve();
						}
					});
				};
			});
		}]);
	});
})(window.angular);