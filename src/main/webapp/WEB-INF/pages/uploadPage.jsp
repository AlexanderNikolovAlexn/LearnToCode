<%--
  Created by IntelliJ IDEA.
  User: Eagle
  Date: 15.4.2016 г.
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>File Uploading Form</title>
</head>
<body>
<h3>File Upload:</h3>
Select a file type to upload: <br />
<form action="UploadServlet" method="POST" enctype="multipart/form-data">
  <div style="color: #FF0000;">${errorUploadMessage}</div>
  <input type="radio" name="fileType" value="json">Json </br>
  <input type="radio" name="fileType" value="xls">Excel </br>
  <input type="radio" name="fileType" value="xml">XML </br>
  </br>

  <input type="file" name="data" />
  <br />
  <br />
  <br />
  <input type="submit" value="Upload File" />
</form>

<div style="color: green;">${successUploadMessage}</div>

</br>

<a href="/">Go back to homepage</a>

</body>
</html>
