<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model frontend\models\Repairs */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="repairs-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'kilometers')->textInput() ?>

    <?= $form->field($model, 'repairdescription')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'state')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'repairtype')->textInput(['maxlength' => true]) ?>

    <?= $form->field($model, 'carId')->textInput() ?>

    <?= $form->field($model, 'contributorId')->textInput() ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>
    </div>

</div>
