<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model backend\models\Companies */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="companies-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'companyname')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'nif')->input('number',[ 'max' => '999999999', 'maxlength' =>'9', 'pattern'=> '[0-9]{9}', 'title' => 'Invalid phone number']) ?>

    <?= $form->field($model, 'email')->textInput(['maxlength' => true,'pattern' =>"[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$", 'title' => 'Invalid email address']) ?>

    <?= $form->field($model, 'phonenumber')->input('number',['max' => '999999999', 'maxlength' =>'9', 'pattern'=> '[0-9]{9}', 'title' => 'Invalid phone number']) ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-secondary']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
