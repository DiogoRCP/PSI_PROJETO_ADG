<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model frontend\models\Cars */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="cars-form">
    <div class="row">
        <div class="col-lg-6">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'vin')->textInput(['maxlength' => true, 'onfocusout' => 'SearchCar(this.value)']) ?>

    <?= $form->field($model, 'brand')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'model')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'modelyear')->input('number', ['min' => 1950, 'max' => 9999]) ?>

    <?= $form->field($model, 'color')->input('color', ['maxlength' => true]) ?>

    <?= $form->field($model, 'carType')->dropDownList(['' => '', 'PASSENGER CAR' => 'PASSENGER CAR',
        'MULTIPURPOSE PASSENGER VEHICLE (MPV)' => 'MULTIPURPOSE PASSENGER VEHICLE (MPV)',
        'TRUCK ' => 'TRUCK ',
        'MOTORCYCLE' => 'MOTORCYCLE']) ?>
    <!--'Small' => 'Small',
        'HatchBack' => 'HatchBack', 'SUV' => 'SUV', '4x4' => '4x4', 'Performance' => 'Performance', 'Pick-up' => 'Pick-up'
    ,'Motorcycle'=>'Motorcycle'-->

        </div>

        <div class="col-lg-6">

    <?= $form->field($model, 'displacement')->input('number', ['maxlength' => true]) ?>

    <?= $form->field($model, 'fuelType')->dropDownList(['' => '', 'Diesel' => 'Diesel', 'Hybrid' => 'Hybrid', 'Electric' => 'Electric', 'Gasoline' => 'Gasoline']) ?>

    <?= $form->field($model, 'registration')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'kilometers')->input('number') ?>

    <?= $form->field($model, 'state')->dropDownList(['' => '', 'Under Repair' => 'Under Repair', 'Repaired' => 'Repaired', 'To Repair' => 'To Repair', 'Pending' => 'Pending']) ?>
            

        </div>
    </div>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-secondary']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
