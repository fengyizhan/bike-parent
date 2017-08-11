(function(angular) {
	'use strict';
	define(['app'], function (app) {
		app.register.controller('PayController', ["$rootScope", "$scope", "$resource", "$timeout", "$q", function($rootScope, $scope, $resource, $timeout, $q){
			/**============================================接口对象定义部分============================================**/
			var Pay = $resource("wallet/information/payrecharge/:username", { "username" : "@username" }, {
				init: { method: 'GET',responseType: "json",url: 'wallet/information/payrecharge/init'},
				exportExcel : { method : 'POST',responseType: "json", url : 'reporter/integrated/payrecharge/exportExcel'}
			});
			/**============================================表单查询定义部分============================================**/
			var selected = {name : "全部"};
			$scope.searchForm = {
				styles:[selected],
				data:{
					"style": selected,
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
			/**============================================页面元素初始化部分============================================**/
			Pay.init(function(response){
				$scope.searchForm.styles = $scope.searchForm.styles.concat(response.styles);
			});
			/**============================================对话框元素初始化部分============================================**/			
			
		}]);
	});
})(window.angular);