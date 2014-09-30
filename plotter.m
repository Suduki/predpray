load('filename.txt');
plot(filename(:,1),filename(:,2),'.-')
hold on
plot(mean(filename(:,1)),mean(filename(:,2)),'r*')
