(function(angular) {
	'use strict';
	define(['app','BMap','resources/components/js/GPSUtils.js'], function (app, BMap) {
		app.register.controller('PileRecordController', ["$rootScope", "$scope", "$resource", "$timeout", "$q", function($rootScope, $scope, $resource, $timeout, $q){
			/**============================================接口对象定义部分============================================**/
			var PileInfo = $resource("api/information/vehicle/:id", { "id" : "@id" }, {
				init : { method : 'GET',responseType: "json", url : 'reporter/integrated/alarm/init'},
				initParks : { method : 'GET', isArray: true, url : 'api/information/park/getAllParks'},
				exportExcel : { method : 'POST',responseType: "json", url : 'exporter/integrated/station/exportExcel'}
			});
			/**============================================表单查询定义部分============================================**/
			var selected = {name : "全部"};
			$scope.searchForm = {
				parks:[selected],
				warnCodes:[selected],
				data:{
					"park" : selected,
					"warnCode":selected,
					"startTime" : $rootScope.start.time,
					"endTime" : $rootScope.end.time
				},
				submit: function(){
					$scope.searchForm.data = angular.copy($scope.searchForm.data);
					$scope.searchForm.data.startTime = $rootScope.start.time;
					$scope.searchForm.data.endTime = $rootScope.end.time;
				}
			};
			/**============================================页面方法定义部分============================================**/
			// 导出Excel
			$scope.exportExcel = function(){
				if ($scope.pagination.hasResults() == null || $scope.pagination.hasResults() == false) {
					$scope.$confrim.init({title:'警告',type:'warning',content : '没有记录可供导出!'}).show();
				} else {
					$scope.$confrim.init({title:'成功',type:'success',content : '导出任务已加入至队列当中, 请耐心等待!'}).show();
					PileInfo.exportExcel($scope.searchForm.data, function(response){
						$rootScope.taskList.unshift(response);
						$rootScope.subscribe(response.id);
					});
				}
			};
			$scope.address = function(target){
				$scope.dialog.title = target.park.name + "报警地点";
				$scope.dialog.data = angular.copy(target);
				var bdpt = GPSUtils.fromWGS84ToBD09(target.lat, target.lng);
				var pt = new BMap.Point(bdpt.lng, bdpt.lat);
				var geoc = new BMap.Geocoder();
				geoc.getLocation(pt, function(rs){
					$scope.$apply(function(){
						$scope.dialog.data.address = rs.address;
					});
				});
				$scope.dialog.show();
			};
			/**============================================页面元素初始化部分==========================================**/
			PileInfo.init(function(response){
				$scope.searchForm.warnCodes = $scope.searchForm.warnCodes.concat(response.warnCodes);
			});
			PileInfo.initParks(function(response){
				$scope.searchForm.parks = $scope.searchForm.parks.concat(response);
			});
			$scope.searchForm.startTime = {
				"open": function($event) {
					$rootScope.start.time.opened = true;
				}
			};
			$scope.searchForm.endTime = {
				"open": function($event) {
					$rootScope.end.time.opened = true;
				}
			};
			/**============================================对话框元素初始化部分========================================**/
		}]);
	});
})(window.angular);