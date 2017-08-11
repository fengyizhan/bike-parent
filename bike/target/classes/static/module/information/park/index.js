var cities = null;
var districts = null;
(function(angular) {
	'use strict';
	define(['app','BMap'], function (app, BMap) {
		app.register.controller('ParkController', ["$rootScope", "$scope", "$resource", "$q", "$timeout", "$filter", function($rootScope, $scope, $resource, $q, $timeout, $filter){
			/**============================================百度地图样式定义部分============================================**/
			var map, drawingManager, styleOptions = {
		        strokeColor:"blue",
		        fillColor:"blue",
		        strokeWeight: 3,
		        strokeOpacity: 0.8,
		        fillOpacity: 0.6,
		        strokeStyle: 'solid',
		        enableEditing: false,
		        enableClicking: true
		    };
			/**============================================接口对象定义部分============================================**/
			var Park = $resource("api/information/park/:id", { "id" : "@id" }, {
				init : { method : 'GET',responseType: "json", url : 'api/information/station/init'},
				validateName : {method : 'GET', url : 'api/information/park/checkname/:name/:id'},
				getRegionsByType : {method : 'GET', isArray : true, url : 'api/information/region/type/:type'},
				getRegionsByParentId : {method : 'GET', isArray : true, url : 'api/information/region/parentId/:parentId'},
				get : { method : 'GET'},
				save : { method : 'PUT'},
				remove : { method : 'DELETE'}
			});
			/**============================================表单查询定义部分============================================**/
			var selected = {name : "全部"};
			$scope.searchForm = {
				provinces:[selected],
				cities:[selected],
				districts:[selected],
				data:{
					"province": selected,
					"city": selected,
					"district": selected
				},
				submit: function(){
					var region = {};
					if ($scope.searchForm.data.district.id != undefined) {
						region = $scope.searchForm.data.district;
					} else if ($scope.searchForm.data.city.id != undefined) {
						region = $scope.searchForm.data.city;
					} else {
						region = $scope.searchForm.data.province;
					}
					$scope.searchForm.data.region = region;
					$scope.searchForm.data = angular.copy($scope.searchForm.data);
				}
			};
			/**============================================页面方法定义部分============================================**/
			$scope.selectProvince = function(item){
				if(!item.id){
					$scope.searchForm.provinces.selected = undefined;
					$scope.searchForm.cities = [selected];
					$scope.searchForm.cities = $scope.searchForm.cities.concat(cities);
					$scope.searchForm.cities.selected = undefined;
					$scope.searchForm.data.city = selected;
					$scope.searchForm.districts = [selected];
					$scope.searchForm.districts = $scope.searchForm.districts.concat(districts);
					$scope.searchForm.districts.selected = undefined;
					$scope.searchForm.data.district = selected;
					return;
				}
				Park.getRegionsByParentId({"parentId":item.id},function(response){
					$scope.searchForm.cities = [selected];
					$scope.searchForm.cities = $scope.searchForm.cities.concat(response);
					$scope.searchForm.data.city = selected;
					$scope.searchForm.districts = [selected];
					$scope.searchForm.districts = $scope.searchForm.districts.concat(districts);
					$scope.searchForm.districts.selected = undefined;
					$scope.searchForm.data.district = selected;
				});
			};
			$scope.selectCity = function(item){
				if(!item.id){
					$scope.searchForm.cities.selected = undefined;
					$scope.searchForm.districts = [selected];
					$scope.searchForm.districts = $scope.searchForm.districts.concat(districts);
					$scope.searchForm.districts.selected = undefined;
					$scope.searchForm.data.district = selected;
					return;
				}
				Park.getRegionsByParentId({"parentId":item.id},function(response){
					if (response.length === 0) {
						$scope.searchForm.districts = $scope.searchForm.cities;
					} else {
						$scope.searchForm.districts = [selected];
						$scope.searchForm.districts = $scope.searchForm.districts.concat(response);
					}
					$scope.searchForm.data.district = selected;
				});
			};
			$scope.changeToNum = function(id){
				if (isNaN(id)) {
					$scope.searchForm.data.id = undefined;
				}
			};
			$scope.info = function(target){
				$scope.dialog.editabled = false;
				$scope.dialog.increased = false;
				$scope.dialog.pre();
				$scope.dialog.title = "集中停放区详情";
				$scope.dialog.data = angular.copy(target);
				$scope.dialog.show();
			};
			$scope.add = function(){
				$scope.dialog.editabled = true;
				$scope.dialog.increased = true;
				$scope.dialog.pre();
				$scope.dialog.title = "新增集中停放区";
				$scope.dialog.data = {
					"property" : {
						"province" : selected,
						"city" : selected,
						"district" : selected
					},
					"radius" : 100
				};
				$scope.dialog.cities = [selected];
				$scope.dialog.districts = [selected];
				$scope.dialog.show();
				
				$scope.dialog.submit = function(event){
					event.preventDefault();
					var region = {};
					if ($scope.dialog.data.property.district.id != undefined) {
						region = $scope.dialog.data.property.district;
					} else if ($scope.dialog.data.property.city.id != undefined) {
						region = $scope.dialog.data.property.city;
					} else {
						$scope.$confrim.init({title:'警告',type:'warning',content : "请选择省和市"}).show();
						return;
					}
					$scope.dialog.data.region = region;
					Park.save($scope.dialog.data,function(response){
						$scope.pagination.unshift(response);
                    });
					$scope.dialog.close();
					map.clearOverlays();
				};
			};
			$scope.edit = function(target){
				$scope.dialog.editabled = true;
				$scope.dialog.increased = false;
				$scope.dialog.pre();
				$scope.dialog.title = "修改集中停放区";
				$scope.dialog.data = angular.copy(target);
				Park.getRegionsByParentId({"parentId":target.property.province.id},function(response){
					$scope.dialog.cities = response;
				});
				Park.getRegionsByParentId({"parentId":target.property.city.id},function(response){
					$scope.dialog.districts = [selected];
					$scope.dialog.districts = $scope.dialog.districts.concat(response);
				});
				$scope.dialog.show();
				
				$scope.dialog.submit = function(event){
					event.preventDefault();
					var region = {};
					if ($scope.dialog.data.property.district.id != undefined) {
						region = $scope.dialog.data.property.district;
					} else if ($scope.dialog.data.property.city.id != undefined) {
						region = $scope.dialog.data.property.city;
					} else {
						$scope.$confrim.init({title:'警告',type:'warning',content : "请选择省和市"}).show();
						return;
					}
					$scope.dialog.data.region = region;
					Park.save($scope.dialog.data,function(response){
						$scope.pagination.refresh(response);
                    });
					$scope.dialog.close();
					map.clearOverlays();
				};
			}
			$scope.remove = function(target){
				$scope.$confrim.init({title:'警告',type:'warning',content : '是否要删除该集中停放区?',callback:function(){
					Park.remove({"id":target.id},function(response){
						if (response.state == undefined) {
							$scope.pagination.remove(target);
						} else {
							$scope.$confrim.init({title:'警告',type:'warning',content : response.message}).show();
						}
                    });
				}}).show();
			};
			
			$scope.isNum = function(str){
				return $q(function(resolve, reject) {
					if (str === undefined) {
						resolve();
					} else if (!isNaN(str)) {
				    	resolve();
					} else {
						reject();
					}
				});
			};
			/**============================================页面元素初始化部分============================================**/
			Park.getRegionsByType({"type":"1"}, function(response){
				$scope.searchForm.provinces = $scope.searchForm.provinces.concat(response);
				$scope.dialog.provinces = response;
			});
			/**============================================对话框元素初始化部分============================================**/
			$scope.$on('dialog-completed', function(e, dialog, element){
				dialog.validateName = function(name){
					return $q(function(resolve, reject) {
						if(name === undefined){
							reject();
						}else if(name !== dialog.data.name){
							Park.validateName({"id": dialog.data.id, "name": name}, function(response){
								response.state === true ? reject(): resolve();
							});
						}else{
							resolve();
						}
					});
				};
				
				dialog.selectProvince = function(item){
					if(!item.id){
						dialog.provinces.selected = undefined;
						dialog.cities = [selected];
						dialog.cities = dialog.cities.concat(cities);
						dialog.cities.selected = undefined;
						dialog.data.city = selected;
						dialog.districts = [selected];
						dialog.districts = dialog.districts.concat(districts);
						dialog.districts.selected = undefined;
						dialog.data.district = selected;
						return;
					}
					Park.getRegionsByParentId({"parentId":item.id},function(response){
						dialog.cities = [selected];
						dialog.cities = dialog.cities.concat(response);
						dialog.data.city = selected;
						dialog.districts = [selected];
						dialog.districts = dialog.districts.concat(districts);
						dialog.districts.selected = undefined;
						dialog.data.district = selected;
					});
				};
				dialog.selectCity = function(item){
					if(!item.id){
						dialog.cities.selected = undefined;
						dialog.districts = [selected];
						dialog.districts = dialog.districts.concat(districts);
						dialog.districts.selected = undefined;
						dialog.data.district = selected;
						return;
					}
					Park.getRegionsByParentId({"parentId":item.id},function(response){
						if (response.length === 0) {
							dialog.districts = dialog.cities;
						} else {
							dialog.districts = [selected];
							dialog.districts = dialog.districts.concat(response);
						}
						dialog.data.district = selected;
					});
				};
				
				dialog.validatePhone = function(phone){
					return $q(function(resolve, reject) {
						if (phone === undefined) {
							reject();
						} else {
							var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
						    var isMob=/^((\+?86)|(\(\+86\)))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
						    if(isMob.test(phone)||isPhone.test(phone)){
						    	resolve();
						    } else {
						    	reject();
						    }
						}
					});
				};
				
				dialog.isNum = function(str){
					return $q(function(resolve, reject) {
						if (str === undefined) {
							resolve();
						} else if (!isNaN(str)) {
					    	resolve();
						} else {
							reject();
						}
					});
				};
				
				dialog.pre = function(){
					var preBtn = document.getElementById("preBtn");
					var nextBtn = document.getElementById("nextBtn");
					preBtn.style.display = "none";
					nextBtn.style.display = "initial";
					var stepFirst = document.getElementById("step-1");
					var stepLast = document.getElementById("step-2");
					stepFirst.className = "selected";
					stepLast.className = "disabled";
					var msgDiv = document.getElementById("msgDiv");
					var mapDiv = document.getElementById("mapDiv");
					msgDiv.style.display = "block";
					mapDiv.style.display = "none";
				};
				
				dialog.next = function(){
					var preBtn = document.getElementById("preBtn");
					var nextBtn = document.getElementById("nextBtn");
					preBtn.style.display = "initial";
					nextBtn.style.display = "none";
					var stepFirst = document.getElementById("step-1");
					var stepLast = document.getElementById("step-2");
					stepFirst.className = "disabled";
					stepLast.className = "selected";
					var msgDiv = document.getElementById("msgDiv");
					var mapDiv = document.getElementById("mapDiv");
					msgDiv.style.display = "none";
					mapDiv.style.display = "block";
					map = new BMap.Map("1474160923584",{enableMapClick:false});
					map.enableScrollWheelZoom();
					map.centerAndZoom(new BMap.Point(113.663221, 34.7568711), 13);
					map.enableAutoResize();
					map.clearOverlays();
					if ($scope.dialog.increased == false) {
						var point = new BMap.Point($scope.dialog.data.lng, $scope.dialog.data.lat);
						var marker = new BMap.MarketPoint(point);
						map.addOverlay(marker);
						var polygon = new BMap.Area($scope.dialog.data, styleOptions);
						polygon && map.addOverlay(polygon);
						map.panTo(point);
						if ($scope.dialog.editabled == true) {
							marker.enableDragging();
							marker.addEventListener('dragend', function(event){
								$scope.$apply(function(){
									var allOverlay = map.getOverlays();
									if (allOverlay.length = 2) {
										map.removeOverlay(allOverlay[1]);
									}
									var point = new BMap.Point(marker.getPosition().lng, marker.getPosition().lat);
									var circle = new BMap.Circle(point, $scope.dialog.data.radius, styleOptions);
									map.addOverlay(circle);
									var path = new BMap.PathFromBD09ToWGS84(marker.getPosition());
									$scope.dialog.data.lng = path.lng;
									$scope.dialog.data.lat = path.lat;
								});
							});
						}
					} else {
						drawingManager = new BMapLib.DrawingManager(map, {
							isOpen: false, //是否开启绘制模式
							enableDrawingTool: true, //是否显示工具栏
							enableCalculate: false,
							drawingType: BMAP_DRAWING_MARKER,
							drawingToolOptions: {
						        anchor: BMAP_ANCHOR_TOP_LEFT,
						        offset: new BMap.Size(2, 2),
						        scale: 1,
						        drawingModes : [
						          BMAP_DRAWING_MARKER
						        ]
						    }
						});
						drawingManager.addEventListener("markercomplete", function(e, overlay) {
							$scope.$apply(function(){
								var allOverlay = map.getOverlays();
								if (allOverlay.length > 2) {
									map.removeOverlay(allOverlay[0]);
								}
								var point = new BMap.Point(overlay.getPosition().lng, overlay.getPosition().lat);
								var circle = new BMap.Circle(point, dialog.data.radius, styleOptions);
								map.addOverlay(circle);
								var path = new BMap.PathFromBD09ToWGS84(overlay.getPosition());
								$scope.dialog.data.lng = path.lng;
								$scope.dialog.data.lat = path.lat;
							});
							drawingManager.close();
						});
						drawingManager.close();
					}
				};
			});
		}]);
	});
})(window.angular);