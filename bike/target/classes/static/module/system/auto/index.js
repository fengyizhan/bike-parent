(function(angular){
	'use strict';
	define(['app'], function (app) {
		app.register.controller('AutoController', ['$scope', '$http', '$filter', '$excel', '$timeout', function($scope, $http, $filter, $excel, $timeout){
			var selected = { "name" : "全部" };
			$scope.form = {
				data: {
					authority: $scope.authority.selected,
					starttime: $scope.start.time,
					endtime: $scope.end.time
				},
				submit: function(){
					$scope.form.data.authority = $scope.authority.selected;
					$scope.form.data.starttime = $scope.start.time;
					$scope.form.data.endtime = $scope.end.time;
					$scope.form.data = angular.copy($scope.form.data);
				}
			};
			$scope.export = function(){
				var data = {
					"starttime" : $scope.form.data.starttime.getTime(),
					"endtime" 	: $scope.form.data.endtime.getTime(),
					"authority" : $scope.authority.selected.id
				};
				$excel.exportToExcel('logistics/statistics/temperature/export?', data);
			};
		}]);
	});
})(window.angular);