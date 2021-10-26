<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model frontend\models\CarSearch */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="cars-search">

    <?php $form = ActiveForm::begin([
        'action' => ['index'],
        'method' => 'get',
    ]); ?>

    <?= $form->field($model, 'id') ?>

    <?= $form->field($model, 'vin') ?>

    <?= $form->field($model, 'brand') ?>

    <?= $form->field($model, 'model') ?>

    <?= $form->field($model, 'color') ?>

    <?php // echo $form->field($model, 'carType') ?>

    <?php // echo $form->field($model, 'displacement') ?>

    <?php // echo $form->field($model, 'fuelType') ?>

    <?php // echo $form->field($model, 'registration') ?>

    <?php // echo $form->field($model, 'purschasedate') ?>

    <?php // echo $form->field($model, 'kilometers') ?>

    <?php // echo $form->field($model, 'state') ?>

    <?php // echo $form->field($model, 'userId') ?>

    <div class="form-group">
        <?= Html::submitButton('Search', ['class' => 'btn btn-primary']) ?>
        <?= Html::resetButton('Reset', ['class' => 'btn btn-outline-secondary']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
