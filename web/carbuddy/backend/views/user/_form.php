<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model backend\models\Users */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="users-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'username')->textInput(['maxlength' => true, 'readonly' => true]) ?>

    <?= $form->field($model, 'usertype')->dropDownList(['admin' => 'Admin', 'collaborator' => 'Collaborator', 'client' => 'Client']) ?>

    <?= $form->field($model, 'nif')->textInput(['maxlength' => true, 'readonly' => true]) ?>

    <?= $form->field($model, 'birsthday')->textInput(['readonly' => true]) ?>

    <?= $form->field($model, 'email')->textInput(['readonly' => true, 'maxlength' => true,'pattern' =>"[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$", 'title' => 'Invalid email address']) ?>

    <?= $form->field($model, 'phonenumber')->textInput(['maxlength' => true, 'readonly' => true]) ?>

    <?= $form->field($model, 'registrationdate')->textInput(['readonly' => true]) ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-secondary']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
