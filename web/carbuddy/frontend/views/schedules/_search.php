<?php

use yii\helpers\Html;
use yii\helpers\VarDumper;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model frontend\models\SchedulesSearch */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="schedules-search">
    <?php $form = ActiveForm::begin([
        'action' => ['index'],
        'method' => 'get',
    ]); ?>

    <?= $form->field($model, 'id') ?>

    <?= $form->field($model, 'currentdate') ?>

    <?= $form->field($model, 'schedulingdate') ?>

    <?= $form->field($model, 'repairdescription') ?>

    <?= $form->field($model, 'state') ?>

    <?php // echo $form->field($model, 'repairtype') ?>

    <?php // echo $form->field($model, 'carId') ?>

    <?php // echo $form->field($model, 'companyId') ?>

    <div class="form-group">
        <?= Html::submitButton('Search', ['class' => 'btn btn-primary']) ?>
        <?= Html::resetButton('Reset', ['class' => 'btn btn-outline-secondary']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
