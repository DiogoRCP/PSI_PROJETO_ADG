<?php

/* @var $this yii\web\View */
/* @var $form yii\bootstrap4\ActiveForm */
/* @var $model \frontend\models\ContactForm */

use yii\bootstrap4\Html;
use yii\bootstrap4\ActiveForm;
use yii\captcha\Captcha;

$this->title = 'Contact';
?>
<div class="site-contact">

    <div class="row">
        <div class="col-lg-9">
            <h1><?= Html::encode($this->title) ?></h1>
            <?php $form = ActiveForm::begin(['id' => 'contact-form']); ?>

                <?= $form->field($model, 'name')->label('Name')->textInput(['autofocus' => true]) ?>

                <?= $form->field($model, 'email')->label('E-mail') ?>

                <?= $form->field($model, 'subject')->label('Subject') ?>

                <?= $form->field($model, 'body')->label('Body')->textarea(['rows' => 6]) ?>

                <?= $form->field($model, 'verifyCode')->label('Verify Code')->widget(Captcha::className(), [
                    'template' => '<div class=""><div class="col-lg-12">{image}</div><div class="col-lg-12">{input}</div></div>',
                ]) ?>

                <div class="form-group">
                    <?= Html::submitButton('Submit', ['class' => 'btn btn-secondary', 'name' => 'contact-button']) ?>
                </div>

            <?php ActiveForm::end(); ?>
        </div>
    </div>

</div>
