<?php

/* @var $this yii\web\View */
/* @var $usertypes backend\models\Users */

$this->title = 'My Yii Application';
$userlabels = [];
$userdata = [];
foreach ($usertypes as $usertype) {
    array_push($userlabels, $usertype['usertype']);
    array_push($userdata, $usertype->getUserTypes());
}

include_once 'charts.php';
?>
<div class="site-index">

    <div class="jumbotron text-center bg-transparent">
        <img src="images/logo_white.png" width="300px">
    </div>

    <div class="body-content">

        <div class="row">
            <div class="col-lg-4">
                <h3>Tipos de utilizador</h3>
                <?= chart("pie", "Tipos de utilizador", $userlabels, $userdata) ?>
            </div>
            <div class="col-lg-4">
                <h3>Utilizadores por mês</h3>
                <?= chart("bar", "Tipos de utilizador", $userlabels, $userdata) ?>
            </div>
            <div class="col-lg-4">
                <h3>Carros reparados por mês</h3>
                <?= chart("line", "Tipos de utilizador", $userlabels, $userdata) ?>
            </div>
        </div>

    </div>
</div>
