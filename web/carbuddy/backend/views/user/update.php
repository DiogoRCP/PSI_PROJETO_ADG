<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model backend\models\Users */

$this->title = 'Update User: ' .$model->username.' ('.$model->nif.')';
?>
<div class="users-update">
    <div class="users-content">
    <img src="../images/logo_white.png" width="80">
    <h1><?= Html::encode($this->title) ?></h1>
    <p>
        <?= Html::a('Back', ['index'], ['class' => 'btn btn-dark']) ?>
    </p>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>
    </div>
</div>
