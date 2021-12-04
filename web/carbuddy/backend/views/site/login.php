<?php

/* @var $this yii\web\View */
/* @var $form yii\bootstrap4\ActiveForm */

/* @var $model \common\models\LoginForm */

use yii\bootstrap4\ActiveForm;
use yii\bootstrap4\Html;

$this->title = 'Login';
$buttonimg = '<svg xmlns="http://www.w3.org/2000/svg" width="48" fill="currentColor" class="bi bi-caret-right-fill" viewBox="0 0 24 16">
                <path d="m12.14 8.753-5.482 4.796c-.646.566-1.658.106-1.658-.753V3.204a1 1 0 0 1 1.659-.753l5.48 4.796a1 1 0 0 1 0 1.506z"/>
            </svg>';


?>
<div class="site-login">

    <div class="mt-3 offset-lg-3.5 col-lg-6 contentor">

        <img><?= Html::img('../images/logo_white.png', ['class' => 'logo', 'width' => 100]) ?>
        <?php $form = ActiveForm::begin(['id' => 'login-form', 'enableClientValidation' => false]); ?>

        <?= $form
            ->field($model, 'username')
            ->label('username')
            ->textInput(['placeholder' => $model->getAttributeLabel('username')]) ?>

        <?= $form
            ->field($model, 'password')
            ->label('password')
            ->passwordInput(['placeholder' => $model->getAttributeLabel('password')]) ?>

        <div class="form-group">
            <?= Html::submitButton('Login', ['class' => 'btn btn-primary', 'name' => 'login-button']) ?>
        </div>
        <br>

        <?php ActiveForm::end(); ?>


    </div>
</div>
