(function(angular) {
	'use strict';
	define(['app'], function (app) {
		app.controller('InitController',[
			'$scope',
			'$rootScope',
			'$timeout',
			'$resource',
			'$filter',
			'$fullScreenApi',
			'$document',
			'$interval',
			'localStorageService',
		    function($scope, $rootScope, $timeout,
				$resource, $filter,
				$fullScreenApi, $document,
				$interval, localStorageService) {
			var UserAuthority = $resource("authority/auths", undefined, {
				initUserMenus : {method : 'GET', url : 'authority/menu'},
				initUserAuthorities : {method : 'GET', url: "json/authority.json"}
			});
			var UserTask = $resource("exporter/integrated/task/:id", { "id" : "@id"}, {
				initUserTasks : { method : 'GET', isArray: true, url : 'exporter/integrated/task/list'},
				downfile : { method : 'GET', url : 'exporter/integrated/task/download/:id'},
				remove : { method : 'DELETE'}
			});
			$rootScope.taskList = {};
			$rootScope.start = {
				"time" : new Date(),
				"open": function($event) {
					$rootScope.start.opened = true;
				}
			};
			$rootScope.start.time.setHours(0,0,0,0);
			$rootScope.end = {
				"open": function($event) {
				    $rootScope.end.opened = true;
				},
				"maxDate":new Date(),
				"time":new Date()
			};
			$rootScope.end.time.setHours(24,0,0,0);
			$rootScope.width = angular.element(document).width();
			$rootScope.height = angular.element(document).height() - ($rootScope.width > 768?80:50);
			$rootScope.activeItem = function(event){
				$(event.currentTarget).siblings(":not(this)").removeClass("active");
				$(event.currentTarget).addClass("active");
			};
			// 工程信息列表
			UserTask.initUserTasks().$promise.then(function(response) {
				$rootScope.taskList = response;
			});
			$rootScope.viewFiles = function(){
				var aObj = document.getElementById("taskDialogLink");
				var spanObj = document.getElementsByClassName("remind")[0];
				if (spanObj != null) {
					aObj.removeChild(spanObj);
				}
				$scope.dialog.show();
			};
			$rootScope.jump = function(){
				console.log($rootScope.$index);
				window.location.href = "/index.html";
			};
			var selected = {"name": '全部'};
			$rootScope.stompClient;
			$rootScope.alerts = [];
			$rootScope.menus = [];
			$rootScope.operator = {};
			$rootScope.provinces = [selected];
			$rootScope.cities = [selected];
			$rootScope.districts = [selected];
			$rootScope.stations = [selected];
			//初始化用户菜单
			var menuResponse = localStorageService.get('menu-response');
			if(menuResponse){
				$rootScope.menus = menuResponse.menus;
				$rootScope.operator = menuResponse.operator;
			}else{
				UserAuthority.initUserMenus(function(response){
					$rootScope.menus = response.menus;
					$rootScope.operator = response.operator;
					localStorageService.set('menu-response', response);
				});
			}
			//初始化用户查询权限
//			var authsResponse = localStorageService.get('auths-response');
//			if(authsResponse){
//				$rootScope.treenodes = authsResponse;
////				$rootScope.provinces = $rootScope.provinces.concat(authsResponse);
//			}else{
//			}
			$rootScope.treenodes = UserAuthority.initUserAuthorities();
			
//			$rootScope.selectProvince = function($item){
//				if(!$item.id){
//					$rootScope.provinces.selected = undefined;
//					$rootScope.cities = [selected];
//					$rootScope.districts = [selected];
//					$rootScope.stations = [selected];
//					return;
//				}
//				$rootScope.cities = [selected].concat($item && $item.children ? $item.children : []);
//				$rootScope.selectCity(selected);
//			};
//			$rootScope.selectCity = function($item){
//				if(!$item.id){
//					$rootScope.cities.selected = undefined;
//					$rootScope.districts = [selected];
//					$rootScope.stations = [selected];
//					return;
//				}
//				if($rootScope.provinces.selected === undefined){
//					for(var i = 0; i < $rootScope.provinces.length; i++){
//						var province = $rootScope.provinces[i];
//						var array = $filter('filter')(province.children, {'id':$item.id,'level':2},true);
//						if(array && array[0] !== undefined){
//							$rootScope.provinces.selected = province;
//							$rootScope.cities = province.children;
//							$rootScope.cities.selected = array[0];
//							break;
//						}
//					}
//				}
//				$rootScope.districts = [selected].concat($item && $item.children ? $item.children : []);
//				$rootScope.stations = [selected];
//			};
//			$rootScope.searchCity = function(name){
//				if(name.length > 0){
//					$rootScope.cities = [];
//					if($rootScope.provinces.selected === undefined){
//						$rootScope.provinces.forEach(function(province) {
//							$rootScope.cities = $rootScope.cities.concat($filter('filter')(province.children, {'name':name}));
//						});
//					}else{
//						$rootScope.cities = $filter('filter')($rootScope.provinces.selected.children, {'name':name});
//					}
//				}
//		    };
//		    $rootScope.initDistricts = function(provinces, target, doamin){
//		    	var province = $filter('filter')(provinces, {'id': target.id.substr(0,2) + '0000'}, true)[0];
//		    	doamin['provinces'].selected = province;
//		    	if(province !== undefined){
//		    		doamin['cities'] = province.children;
//		    		if(province.children && province.children.length > 0){
//		    			var city = $filter('filter')(province.children, {'id': target.level === 3 ? target.parentId:target.id}, true)[0];
//		    			doamin['cities'].selected = city;
//		    			if(city !== undefined){
//		    				doamin['districts'] = city.children;
//		    				if(city.children && city.children.length > 0 && target.level === 3){
//		    					doamin['districts'].selected = target;
//		    				}
//		    			}
//		    		}
//		    	}
//		    };
//		    $rootScope.cleanDistricts = function(provinces, doamin){
//		    	doamin['provinces'].selected = undefined;
//		    	doamin['cities'] = undefined;
//		    	doamin['districts'] = undefined;
//		    };
//		    
//		    $rootScope.authority = {};
//		    $rootScope.$watch(function(){
//				return $rootScope.stations.selected
//						&& $rootScope.stations.selected.id !== undefined ? $scope.stations.selected
//						: $scope.districts.selected
//								&& $scope.districts.selected.id !== undefined ? $scope.districts.selected
//								: $scope.cities.selected
//										&& $scope.cities.selected.id !== undefined ? $scope.cities.selected
//										: $scope.provinces.selected
//												&& $scope.provinces.selected.id !== undefined ? $scope.provinces.selected
//												: undefined;
//			}, function(selected){
//				$rootScope.authority.selected = angular.copy(selected);
//				if(selected && selected.children !== undefined){
//					delete $rootScope.authority.selected.children;
//				}
//				if(selected && selected.isParent !== undefined){
//					delete $rootScope.authority.selected.isParent;
//				}
//			});
//			$rootScope.stompClient = Stomp.over(new SockJS('http://localhost:8820/monitor/socket'));
		    $rootScope.stompClient = Stomp.over(new SockJS('/monitor/socket'));
			$rootScope.stompClient.debug = undefined;
			$rootScope.stompClient.connect({}, function(frame) {
				$rootScope.stompClient.subscribe('/topic/warning', function(response) {
					$rootScope.$apply(function(){
						$rootScope.alerts[0] = JSON.parse(response.body);
						console.log($rootScope.alerts[0]);
					});
				});
				$rootScope.$broadcast('stomp-completed');
            });
			$scope.$on('$destroy', function() {
				if ($rootScope.stompClient) {
					$rootScope.stompClient.disconnect();
	            }
				localStorageService.clearAll();
	        });
			$scope.$on('$viewContentLoaded',function(){
	        	if($rootScope.$confrim){
					$rootScope.$confrim.close();
	            }
	        	if($rootScope.width > 768){
	        		$(".nano").nanoScroller({ scroll: 'top' });
	        	}
	        });
			
			$rootScope.subscribe = function(id){
				var subscription = $rootScope.stompClient.subscribe('/topic/schedule/'+ id, function(response) {
					$scope.$apply(function(){
						var schedule = JSON.parse(response.body);
						console.log(schedule);
						if (schedule.progress.index == 3) {
							var aObj = document.getElementById("taskDialogLink");
							var spanObj = document.getElementsByClassName("remind")[0];
							if (spanObj == null) {
								spanObj = document.createElement("span");
								spanObj.className = "remind";
								spanObj.innerHTML = "1"; 
								aObj.appendChild(spanObj);
							} else {
								spanObj.innerHTML = parseInt(spanObj.innerHTML) + 1;
							}
						}
						angular.forEach($rootScope.taskList, function(item){
							if (schedule.id == item.id) {
								item.progress = schedule.progress;
							}
						});
						if (schedule.progress.index == 3) {
							subscription.unsubscribe();
						}
					});
				});
			};
			/**============================================对话框元素初始化部分============================================**/
			$scope.$on('dialog-completed', function(e, dialog, element){
				// 下载文件
				dialog.download = function(item) {
					UserTask.downfile({"id": item.id}, function(response){
						var openLink = document.createElement("a");
						document.body.appendChild(openLink);
						openLink.href = response.url;
						openLink.target = '_blank';
						openLink.click();
						document.body.removeChild(openLink);
					});
				};
				
				// 删除文件
				dialog.remove = function(target){
					$scope.$confrim.init({title:'警告',type:'warning',content : '是否要删除该文件?',callback:function(){
						UserTask.remove({"id":target.id},function(response){
							var tasks = $rootScope.taskList;
							for (var i=0; i<tasks.length; i++) {
								var item = tasks[i];
								if (target.id == item.id) {
									tasks.splice(i, 1);
								}
							}
							$rootScope.taskList = tasks;
	                    });
					}}).show();
				};
			});
//			$rootScope.stompClient.connected? subscribe():$scope.$on('stomp-completed', subscribe);
		}]);
	});
})(window.angular);