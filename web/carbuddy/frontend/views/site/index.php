<?php

/* @var $this yii\web\View */

$this->title = 'CarBuddy';
?>
<div class="site-index">

    <div class="jumbotron text-center bg-transparent">
        <img src="../images/logo_white.png" width="250">
    </div>

    <div class="body-content" style="margin-top: -7rem; margin-bottom: 2rem">

        <?php
        echo $this->render('about');
        ?>

    </div>
</div>
