(function(angular) {
	'use strict';
	define(['app'], function (app) {
		app.register.controller('RechargeRecordController', ["$rootScope", "$scope", "$resource", "$timeout", "$q", function($rootScope, $scope, $resource, $timeout, $q){
			/**============================================接口对象定义部分============================================**/
			var UserRechargeInfo = $resource("api/information/vehicle/:id", { "id" : "@id" }, {
				init: { method: 'GET', url: 'wallet/information/recharge/init'},
				exportExcel : { method : 'POST',responseType: "json", url : 'exporter/integrated/alarm/exportExcel'}
			});
			/**============================================表单查询定义部分============================================**/
			var selected = {name : "全部"};
			$scope.searchForm = {
				styles:[selected],
				data:{
					"style" : selected,
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
					UserRechargeInfo.exportExcel($scope.searchForm.data, function(response){
						$rootScope.taskList.unshift(response);
						$rootScope.subscribe(response.id);
					});
				}
			};
			/**============================================页面元素初始化部分==========================================**/
			UserRechargeInfo.init(function(response){
				$scope.searchForm.styles = $scope.searchForm.styles.concat(response.styles);
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