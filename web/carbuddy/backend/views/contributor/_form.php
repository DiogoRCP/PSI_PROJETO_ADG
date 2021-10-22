<?php

use yii\helpers\Html;
use yii\widgets\ActiveForm;

/* @var $this yii\web\View */
/* @var $model backend\models\Contributors */
/* @var $modelUsers backend\models\Users */
/* @var $modelCompanies backend\models\Companies */
/* @var $form yii\widgets\ActiveForm */

// Carregar Users para a dropDownList
$UsersList = ['' => ''];
foreach ($modelUsers as $modelUser) {
    $UsersList += [$modelUser['id'] => $modelUser['username']];
}

// Carregar Companies para a dropDownList
$CompaniesList = ['' => ''];
foreach ($modelCompanies as $modelCompany) {
    $CompaniesList += [$modelCompany['id'] => $modelCompany['companyname']];
}

?>

<div class="contributors-form">

    <?php $form = ActiveForm::begin();?>

    <?= $form->field($model, 'userId')->dropDownList($UsersList) ?>

    <?= $form->field($model, 'companyId')->dropDownList($CompaniesList) ?>

    <?= $form->field($model, 'speciality')->dropDownList([''=> '', 'Mechanical' => 'Mechanical', 'Electrician' => 'Electrician', 'Painter' => 'Painter', 'Locksmith' => 'Locksmith']) ?>

    <div class="form-group">
        <?= Html::submitButton('Save', ['class' => 'btn btn-success']) ?>
    </div>

    <?php ActiveForm::end(); ?>

</div>
