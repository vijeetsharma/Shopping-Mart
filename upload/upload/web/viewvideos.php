
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Charli Events-View Videos</title>
</head>

<body>
   <div class="page-container">
 
	<div class="left-content">
	   <div class="inner-content">
				<?php 
				include('header.php');
				?>
		</div>
		<!--inner-content closed-->
		
				<div class="outter-wp">
								
										<div class="custom-widgets">
												   <div class="row-one">
												 		<h3 class="inner-tittle two">Video Links </h3>
													<div class="graph">
															<div class="tables" style="overflow:scroll; height:60%;">
															
																<table class="table table-hover"> 
																<thead> 
																<tr> 
																<th>Sr. No</th> 
																<th>links</th> 
																<th>Delete</th> 
																<th>Edit</th> 
																</tr> 
																</thead> 
																<tbody>
																<?php 
																include('connection.php');

																$sel = 'SELECT * FROM video';
																		$qer=$cn->query($sel);
																		$r=0;
																		while($qe=mysqli_fetch_array($qer))	
																		{	
																			$r++;
																																
																	 ?> 
																<tr> 
																<th scope="row"><?php echo $r; ?></th> 
																<td><?php echo $qe['link'];?></td> 
																<td><a href="delvideo.php?vid=<?php echo $qe['id'];?>" style="text-decoration:underline;"><button type="submit" class="btn btn-primary" name="delete">Delete</button></a></td> 
																<td><a href="updatevideo.php?vid=<?php echo $qe['id'];?>" style="text-decoration:underline;"><button type="submit" class="btn btn-default">Edit</button></a></td> 
																</tr> 
																<?php
																 } ?>
																</tbody> 
																</table>
															</div>
												
													</div>
													</div><!--row closed-->
												</div>
					</div>	
					<!--outter-wp closed-->
		<?php include('footer.php'); ?>
	</div> <!--left-wp closed-->
</div> <!--page container closed-->

</body>
</html>
