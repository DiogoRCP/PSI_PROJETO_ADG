<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model frontend\models\Cars */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="cars-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'vin')->textInput(['maxlength' => true, 'onfocusout'=>'SearchCar(this.value)']) ?>

    <?= $form->field($model, 'purschasedate')->input('date') ?>

    <?= $form->field($model, 'brand')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'model')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'color')->input('color',['maxlength' => true]) ?>

    <?= $form->field($model, 'carType')->dropDownList(['' => '','Small' => 'Small',
        'HatchBack' => 'HatchBack', 'SUV' => 'SUV', '4x4' => '4x4', 'Performance' => 'Performance', 'Pick-up' => 'Pick-up'
    ,'Motorcycle'=>'Motorcycle'])  ?>

    <?= $form->field($model, 'displacement')->input('number',['maxlength' => true]) ?>

    <?= $form->field($model, 'fuelType')->dropDownList(['' => '','Diesel' => 'Diesel', 'Hybrid' => 'Hybrid', 'Electric' => 'Electric', 'Gasoline' => 'Gasoline'])  ?>

    <?= $form->field($model, 'registration')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'kilometers')->input('number') ?>

    <?= $form->field($model, 'state')->dropDownList(['' => '','Under Repair' => 'Under Repair', 'Repaired' => 'Repaired', 'To Repair' => 'To Repair', 'Pending' => 'Pending'])  ?>

    <?= $form->field($model, 'userId')->textInput() ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
