clc
clear all

f = [1 1 1 1 1 1 1];
A = [-1 0 0 -1 -1 -1 -1;
     -1 -1 0 0 -1 -1 -1;
     -1 -1 -1 0 0 -1 -1;
     -1 -1 -1 -1 0 0 -1;
     -1 -1 -1 -1 -1 0 0;
     0 -1 -1 -1 -1 -1 0;
     0 0 -1 -1 -1 -1 -1;
     ];
 b = [-8;
     -6;
     -5;
     -4;
     -6;
     -7;
     -9;
      ];
 lb = zeros(7,1);
 %options = optimoptions('intlinprog', 'Display', 'iter');
 options = optimoptions('linprog', 'Display', 'off');
% intcon = [1;
% 2;
%           3;
%           4;
%           5;
%           6;
%           7;];
[x, fval, exitflag, output] = linprog(f', A, b, [], [], lb, [])