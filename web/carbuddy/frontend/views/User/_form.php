<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model frontend\models\Users */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="users-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'username')->textInput(['maxlength' => true, 'readonly'=>true]) ?>

    <?= $form->field($model, 'nif')->textInput(['maxlength' => true, 'readonly'=>true]) ?>

    <?= $form->field($model, 'email')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'password_hash')->label('Password')->textInput(['type' => 'password', 'id' => 'pass1',
        'onFocus'=>'SecondPassword()']) ?>

    <?= Html::label('Repeat Password', 'pass2', ['id'=> 'passRepeatLabel', 'style' => 'display: none']) ?>
    <?=Html::textInput("", "", ['type' => 'password', 'id' => 'pass2', 'class' => 'form-control','onkeyup'=>'PasswordVerify()', 'style' => 'display: none']) ?>
    <?=Html::tag('p', '', ['id'=> 'mensagemLabel', 'style' => 'display: none'])?>
    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-secondary', 'id' => 'btSave']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
