<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model frontend\models\UsersSearch */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="users-search">

    <?php $form = ActiveForm::begin([
        'action' => ['index'],
        'method' => 'get',
    ]); ?>

    <?= $form->field($model, 'id') ?>

    <?= $form->field($model, 'username') ?>

    <?= $form->field($model, 'password_hash') ?>

    <?= $form->field($model, 'verification_token') ?>

    <?= $form->field($model, 'auth_key') ?>

    <?php // echo $form->field($model, 'status') ?>

    <?php // echo $form->field($model, 'updated_at') ?>

    <?php // echo $form->field($model, 'created_at') ?>

    <?php // echo $form->field($model, 'usertype') ?>

    <?php // echo $form->field($model, 'nif') ?>

    <?php // echo $form->field($model, 'birsthday') ?>

    <?php // echo $form->field($model, 'email') ?>

    <?php // echo $form->field($model, 'phonenumber') ?>

    <?php // echo $form->field($model, 'registrationdate') ?>

    <div class="form-group">
        <?= Html::submitButton('Search', ['class' => 'btn btn-primary']) ?>
        <?= Html::resetButton('Reset', ['class' => 'btn btn-outline-secondary']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
