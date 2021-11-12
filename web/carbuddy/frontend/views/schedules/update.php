<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model frontend\models\Schedules */
/* @var $modelCompanies frontend\models\Companies */
/* @var $modelCars frontend\models\Cars */

$this->title = 'Update Schedule: ' .$model->car->brand." ".$model->car->model." (".$model->company->companyname." - ".$model->schedulingdate.")";

?>
<div class="schedules-update">
    <div class="schedules-content">
        <img src="../images/logo_white.png" width="80">
    <h1><?= Html::encode($this->title) ?></h1>
        <p>
            <?= Html::a('Back', ['index'], ['class' => 'btn btn-dark']) ?>
        </p>
    <?= $this->render('_form', [
        'model' => $model, 'modelCompanies' => $modelCompanies, 'modelCars' => $modelCars,
    ]) ?>
    </div>
</div>
