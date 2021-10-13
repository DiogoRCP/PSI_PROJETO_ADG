<?php

/* @var $this yii\web\View */
/* @var $form yii\bootstrap4\ActiveForm */

/* @var $model \common\models\LoginForm */

use yii\bootstrap4\ActiveForm;
use yii\bootstrap4\Html;

$this->title = 'Login';
?>
<div class="site-login">
    <div class="mt-5 offset-lg-3 col-lg-6 container">
        <img><?= Html::img('images/logo_white.png', ['class'=>'logo', 'width'=>150]) ?>

        <?php $form = ActiveForm::begin(['id' => 'login-form']); ?>

        <?= $form->field($model, 'username')->textInput(['class'=>'form-control is-invalid textform', 'autofocus' => true, 'placeholder'=>'Username']) ?>

        <?= $form->field($model, 'password')->passwordInput(['class'=>'form-control is-invalid textform', 'placeholder'=>'Password']) ?>

        <?= $form->field($model, 'rememberMe')->checkbox() ?>

        <div class="form-group">
            <?= Html::submitButton('Login', ['class' => 'btn btn-primary btn-block', 'name' => 'login-button']) ?>
        </div>

        <?php ActiveForm::end(); ?>
    </div>
</div>
