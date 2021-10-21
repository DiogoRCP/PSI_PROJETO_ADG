<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model backend\models\Companies */

$this->title = 'Update Company: ' . $model->companyname.' ('.$model->nif.')';
?>
<div class="companies-update">

    <h1><?= Html::encode($this->title) ?></h1>
    <?= Html::a('Back', ['index'], ['class' => 'btn btn-primary']) ?>
    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
