Framework cấu trúc hóa mã nguồn nhờ đó việc bảo trì trở nên dễ dàng hơn
Hiện tại có 3 loại framework chính:			
1. Data drivern test framework			
Hỗ trợ thưc thi mẫu test case nhiều lần với những giá trị input khác nhau. 			
Cho phép người dùng lưu trữ dữ liệu test vào một cơ sở dữ liệu bên ngoài (Property file, xml files, excel files, text files, CSV files, ODBC repositories..)			
Data được lưu dưới dạng cặp khóa - giá trị (Key-Values)			
2. Keyword driven test framework
Là dạng mở rộng của data driven testing framework không chỉ tách riêng data test khỏi mã nguồn mà còn thiết lập một tập mã nguồn được gọi là Keywords trong file data test bên ngoài.
Notes:
Trong ví dụ trên, những keyword như login, clickLink và verifyLink được định nghĩa trong mã nguồn
Tất cả keyword đều có thể được sử dụng nhiều lần trong một test case
Cột locator được sử dụng để xác định web elements trên màn hình hoặc data test cần input
3. Hybrid test framework
Là dạng framework kết hợp và tận dụng những ưu điểm của Keyword và Data driven framework

KIẾN TRÚC WEBDRIVER 2 USING HYBRID TESTING FRAMEWORK

com.totsy.config						
config.properties	"Thông tin cấu hình hệ thống:
- Testing URL
- Login Credential
- Browser Type
- Screenshot
- Report file name"					
or.properties	Chứa thông tin Xpath của Object					
com.totsy.log						
	Application.log	Chứa thông tin log của hệ thống					
com.totsy.test						
	Constants.java	Chứa giá trị hằng số					
	DriverScript.java	Set as StartUp project, chứa thông tin hàm Main. Thực thi keyword và tạo report.					
	Keywords.java	Chứa định nghĩa Keywords được sử dụng trong hệ thống					
com.totsy.utils						
	ReportUtil.java	Chứa các hàm sử dụng trong việc Report					
	SendMail.java	Chứa các hàm sử dụng trong việc Send Mail					
	Zip.java	Chứa các hàm sử dụng trong việc nén file					
com.totsy.xls						
	Suite.xls	Chứa thông tin các file Test Case					
	Product.xls	Test Case liên quan đến Product					
	User.xls	Test Case liên quan đến User					
	OR-Keywords.xls	Resource chứa thông tin keyword, page, web element trên màn hình					
	test						
App.java	Sample scripts					