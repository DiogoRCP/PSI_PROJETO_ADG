<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model frontend\models\Schedules */
/* @var $companyName */
/* @var $modelCars frontend\models\Cars */


$this->title = $companyName . " Appointment";

?>
<div class="schedules-create">
    <div class="schedules-content">
        <h1><?= Html::encode($this->title) ?></h1>
        <p>
            <?= Html::a('Back', "javascript:history.back()", ['class' => 'btn btn-light']) ?>
        </p>
        <?= $this->render('_form', [
            'model' => $model, 'modelCars' => $modelCars,
        ]) ?>
    </div>
</div>
