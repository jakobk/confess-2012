<html>
  <head>
    <title>Acme.com - Show Product Name</title>
  </head>
  <body>
    <h1>Acme.com - Show Product Name</h1>

<?php
    mysql_connect("localhost", "root", "");
    mysql_select_db("confess");
    $query = mysql_query("SELECT name FROM product WHERE id =".$_GET['id']) or die("Invalid query");
    if ($row = mysql_fetch_array($query))
    {
        echo $row['name'];
    }
    mysql_close();
?>
      
  </body>
</html>
