<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model backend\models\Contributors */
/* @var $form yii\widgets\ActiveForm */
?>

<div class="contributors-form">

    <?php $form = ActiveForm::begin(); ?>

    <?= $form->field($model, 'speciality')->dropDownList(['Mechanical' => 'Mechanical', 'Electrician' => 'Electrician', 'Painter' => 'Painter', 'Locksmith' => 'Locksmith']) ?>

    <?= $form->field($model, 'companyId')->textInput() ?>

    <?= $form->field($model, 'userId')->textInput() ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
