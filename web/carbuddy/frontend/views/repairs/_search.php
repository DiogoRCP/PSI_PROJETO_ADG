<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model frontend\models\RepairsSearch */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="repairs-search">

    <?php $form = ActiveForm::begin([
        'action' => ['index'],
        'method' => 'get',
    ]); ?>

    <?= $form->field($model, 'id') ?>

    <?= $form->field($model, 'kilometers') ?>

    <?= $form->field($model, 'repairdate') ?>

    <?= $form->field($model, 'repairdescription') ?>

    <?= $form->field($model, 'state') ?>

    <?php // echo $form->field($model, 'repairtype') ?>

    <?php // echo $form->field($model, 'carId') ?>

    <?php // echo $form->field($model, 'contributorId') ?>

    <div class="form-group">
        <?= Html::submitButton('Search', ['class' => 'btn btn-primary']) ?>
        <?= Html::resetButton('Reset', ['class' => 'btn btn-outline-secondary']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
